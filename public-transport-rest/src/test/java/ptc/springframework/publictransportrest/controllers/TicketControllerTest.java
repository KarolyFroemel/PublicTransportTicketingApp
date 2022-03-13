package ptc.springframework.publictransportrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ptc.springframework.publictransportrest.configurations.TestSecurityConfig;
import ptc.springframework.publictransportrest.keycloak.KeycloakService;
import ptc.springframework.publictransportrest.testdata.TicketTestData;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class)
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TicketControllerTest {

    private final UUID USER_DAVID_BECKHAM_ID = UUID.fromString("1958fb4a-2f76-480c-8df2-6045f3c017a5");
    private final UUID USER_DAVID_SILVA_ID = UUID.fromString("efd88677-79d7-42c9-bd97-f756a1110f1e");
    private final UUID TICKET_FOR_VALIDATION = UUID.fromString("83f90f5c-28ad-4536-8585-83af477c5112");
    private final UUID TICKET_TO_REFUND = UUID.fromString("6fa6538e-3c27-42fe-a51a-fbf3f581e804");
    private final UUID TICKET_IS_NOT_ENFORCEABLE = UUID.fromString("fdf2da00-e79b-449b-9884-b39f826c314c");
    private final UUID TICKET_IS_NOT_BELONG_TO_USER = UUID.fromString("6e4fe7b8-f5d8-4930-bc94-5cc4e9d55ead");


    @Autowired
    private MockMvc mvc;

    private final TicketTestData ticketTestData =  new TicketTestData();

    @MockBean
    private KeycloakService keycloakService;

    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @BeforeEach
    public void init() {
        Mockito.lenient().when(keycloakService.getUserId()).thenReturn(USER_DAVID_BECKHAM_ID);
    }

    @Test
    @DisplayName("Purchase ticket.")
    @Order(1)
    void purchaseTicket() throws Exception {
        mvc.perform(post("/ticket/purchaseTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTestData.getPurchaseTicketModel())))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Purchase ticket with bad date.")
    @Order(2)
    void purchaseTicketWithBadDate() throws Exception {
        mvc.perform(post("/ticket/purchaseTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTestData.getPurchaseTicketModelDateInThePast())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("TICKET002"))
                .andExpect(jsonPath("$.message").value("Incorrect date."))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket validity date is in the past."));
    }

    @Test
    @DisplayName("Purchase non existing ticket type.")
    @Order(3)
    void purchaseNonExistingTicketType() throws Exception {
        mvc.perform(post("/ticket/purchaseTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTestData.getPurchaseTicketModelNonExistingTicketType())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TICKET_TYPE001"))
                .andExpect(jsonPath("$.message").value("Ticket type not found!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket type not found by id in database!"));
    }

    @Test
    @DisplayName("Purchase with bad balance.")
    @Order(4)
    void purchaseTicketTypeWithBadBalance() throws Exception {
        Mockito.lenient().when(keycloakService.getUserId()).thenReturn(USER_DAVID_SILVA_ID);
        mvc.perform(post("/ticket/purchaseTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTestData.getPurchaseTicketModel())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode").value("USER002"))
                .andExpect(jsonPath("$.message").value("User has less balance than needed."))
                .andExpect(jsonPath("$.detailedMessage").value("User has less money than the selected ticket price needed."));
    }

    @Test
    @DisplayName("Validate ticket.")
    @Order(5)
    void validateTicket() throws Exception {
        mvc.perform(put("/ticket/validateTicket/{ticketId}", TICKET_FOR_VALIDATION)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validate non existing ticket.")
    @Order(6)
    void validateNonExistingTicket() throws Exception {
        mvc.perform(put("/ticket/validateTicket/{ticketId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TICKET001"))
                .andExpect(jsonPath("$.message").value("Ticket not found!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket not found by id in database!"));
    }

    @Test
    @DisplayName("Ticket not belong to user.")
    @Order(7)
    void validateNotUserTicket() throws Exception {
        mvc.perform(put("/ticket/validateTicket/{ticketId}", TICKET_IS_NOT_BELONG_TO_USER)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("TICKET005"))
                .andExpect(jsonPath("$.message").value("Ticket is not belong to user!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket is not belong to user!"));
    }

    @Test
    @DisplayName("Validate not enforceable ticket.")
    @Order(8)
    void validateNotEnforceableTicket() throws Exception {
        mvc.perform(put("/ticket/validateTicket/{ticketId}", TICKET_IS_NOT_ENFORCEABLE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("TICKET003"))
                .andExpect(jsonPath("$.message").value("Ticket is not enforceable!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket is not enforceable!"));
    }


    @Test
    @DisplayName("Refund non existing ticket.")
    @Order(9)
    void refundNonExistingTicket() throws Exception {
        mvc.perform(delete("/ticket/refund/{ticketId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TICKET001"))
                .andExpect(jsonPath("$.message").value("Ticket not found!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket not found by id in database!"));
    }

    @Test
    @DisplayName("Refund validated ticket.")
    @Order(10)
    void refundValidatedTicket() throws Exception {
        mvc.perform(delete("/ticket/refund/{ticketId}", TICKET_FOR_VALIDATION)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("TICKET004"))
                .andExpect(jsonPath("$.message").value("Ticket is not refundable!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket is not refundable!"));
    }

    @Test
    @DisplayName("Refund ticket.")
    @Order(11)
    void refundTicket() throws Exception {
        mvc.perform(delete("/ticket/refund/{ticketId}", TICKET_TO_REFUND)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Refund not user's ticket.")
    @Order(12)
    void refundTicketNotUsersTicket() throws Exception {
        mvc.perform(delete("/ticket/refund/{ticketId}", TICKET_IS_NOT_BELONG_TO_USER)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("TICKET005"))
                .andExpect(jsonPath("$.message").value("Ticket is not belong to user!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket is not belong to user!"));
    }

    @Test
    @DisplayName("Search in ticket ASC.")
    @Order(13)
    void searchTicketASC() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/ticket/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(ticketTestData.getTicketSearchModelASC())))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "1"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "1"));
    }

    @Test
    @DisplayName("Search in ticket DESC.")
    @Order(14)
    void searchTicketDESC() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/ticket/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(ticketTestData.getTicketSearchModelDESC())))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "1"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "1"));
    }

    @Test
    @DisplayName("Get non existing ticket.")
    @Order(15)
    void getNonExistingTicket() throws Exception {
        mvc.perform(get("/ticket/{ticketId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TICKET001"))
                .andExpect(jsonPath("$.message").value("Ticket not found!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket not found by id in database!"));
    }

    @Test
    @DisplayName("Get other user ticket.")
    @Order(16)
    void getOtherUserTicket() throws Exception {
        mvc.perform(get("/ticket/{ticketId}", TICKET_IS_NOT_BELONG_TO_USER)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("TICKET005"))
                .andExpect(jsonPath("$.message").value("Ticket is not belong to user!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket is not belong to user!"));
    }

    @Test
    @DisplayName("Get user ticket.")
    @Order(17)
    void getUserTicket() throws Exception {
        mvc.perform(get("/ticket/{ticketId}", TICKET_FOR_VALIDATION)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}