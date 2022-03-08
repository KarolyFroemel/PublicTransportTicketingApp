package ptc.springframework.publictransportrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import contract.ticket.model.TicketTypeModel;
import contract.ticket.model.TicketTypeSearchRequestModel;
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
import ptc.springframework.publictransportrest.testdata.TicketTypeTestData;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class)
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TicketTypeControllerTest {

    private final UUID USER_ID = UUID.fromString("eb5e3e64-1153-410d-bfc7-9988766c82ac");
    private final String SINGLE_TICKET_ID = "dbb5103f-4bfe-42e3-b6ab-1ca7cbcde047";
    private final String NON_EXISTING_TICKET_ID = "086cd646-7adb-49df-97f8-7ee10717929c";

    @Autowired
    private MockMvc mvc;

    private final TicketTypeTestData ticketTypeTestData =  new TicketTypeTestData();

    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private KeycloakService keycloakService;

    @BeforeEach
    public void init() {
        Mockito.lenient().when(keycloakService.getUserId()).thenReturn(USER_ID);
    }

    @Test
    @DisplayName("Get all ticket types")
    @Order(1)
    void getTicketTypes() throws Exception {
        mvc.perform(get("/ticketType", SINGLE_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("Get ticket by id.")
    @Order(2)
    void getTicketTypeById() throws Exception {
        TicketTypeModel result = ticketTypeTestData.getSingleTicketTypeModel();
        mvc.perform(get("/ticketType/{id}", SINGLE_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(result.getId().toString()))
                .andExpect(jsonPath("$.name").value(result.getName()))
                .andExpect(jsonPath("$.description").value(result.getDescription()))
                .andExpect(jsonPath("$.price").value(result.getPrice()))
                .andExpect(jsonPath("$.expirationTime").value(result.getExpirationTime()))
                .andExpect(jsonPath("$.isEnforceable").value(result.getIsEnforceable()));
    }

    @Test
    @DisplayName("Create new ticket type.")
    @Order(3)
    void createNewTicketType() throws Exception {
        TicketTypeModel ticketTypeModel = ticketTypeTestData.getNewTicketTypeModel();
        mvc.perform(post("/ticketType")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTypeModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(ticketTypeModel.getName()))
                .andExpect(jsonPath("$.description").value(ticketTypeModel.getDescription()))
                .andExpect(jsonPath("$.price").value(ticketTypeModel.getPrice()))
                .andExpect(jsonPath("$.expirationTime").value(ticketTypeModel.getExpirationTime()))
                .andExpect(jsonPath("$.isEnforceable").value(ticketTypeModel.getIsEnforceable()));
    }

    @Test
    @DisplayName("Search in ticket types.")
    @Order(4)
    void searchTicketType() throws Exception {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = ticketTypeTestData.getTicketTypeSearchRequestModelSortByNameASC();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/ticketType/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(ticketTypeSearchRequestModel)))
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
    @DisplayName("Search in ticket types DESC.")
    @Order(5)
    void searchTicketTypeDESC() throws Exception {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = ticketTypeTestData.getTicketTypeSearchRequestModelSortByNameDesc();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/ticketType/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(ticketTypeSearchRequestModel)))
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
    @DisplayName("Search in ticket types empty.")
    @Order(6)
    void searchTicketTypeEmpty() throws Exception {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = ticketTypeTestData.getTicketTypeSearchRequestModelEmpty();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/ticketType/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(ticketTypeSearchRequestModel)))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "2"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "4"));
    }

    @Test
    @DisplayName("Search in ticket types with lambda.")
    @Order(7)
    void searchTicketTypeLambda() throws Exception {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = ticketTypeTestData.getTicketTypeSearchRequestModelSortByNameASC();
        TicketTypeModel result = ticketTypeTestData.getSingleTicketTypeModel();

        mvc.perform(post("/ticketType/searchLambda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTypeSearchRequestModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(result.getId().toString()))
                .andExpect(jsonPath("$.[0].name").value(result.getName()))
                .andExpect(jsonPath("$.[0].description").value(result.getDescription()))
                .andExpect(jsonPath("$.[0].price").value(result.getPrice()))
                .andExpect(jsonPath("$.[0].expirationTime").value(result.getExpirationTime()))
                .andExpect(jsonPath("$.[0].isEnforceable").value(result.getIsEnforceable()));

    }

    @Test
    @DisplayName("Search in ticket types with lambda by description.")
    @Order(8)
    void searchTicketTypeLambdaByDescription() throws Exception {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = ticketTypeTestData.getTicketTypeSearchRequestModelSortByDescriptionASC();
        TicketTypeModel result = ticketTypeTestData.getSingleTicketTypeModel();

        mvc.perform(post("/ticketType/searchLambda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTypeSearchRequestModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(result.getId().toString()))
                .andExpect(jsonPath("$.[0].name").value(result.getName()))
                .andExpect(jsonPath("$.[0].description").value(result.getDescription()))
                .andExpect(jsonPath("$.[0].price").value(result.getPrice()))
                .andExpect(jsonPath("$.[0].expirationTime").value(result.getExpirationTime()))
                .andExpect(jsonPath("$.[0].isEnforceable").value(result.getIsEnforceable()));

    }

    @Test
    @DisplayName("Search in ticket types with lambda desc.")
    @Order(9)
    void searchTicketTypeLambdaDesc() throws Exception {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = ticketTypeTestData.getTicketTypeSearchRequestModelSortByNameDesc();
        TicketTypeModel result = ticketTypeTestData.getSingleTicketTypeModel();

        mvc.perform(post("/ticketType/searchLambda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTypeSearchRequestModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(result.getId().toString()))
                .andExpect(jsonPath("$.[0].name").value(result.getName()))
                .andExpect(jsonPath("$.[0].description").value(result.getDescription()))
                .andExpect(jsonPath("$.[0].price").value(result.getPrice()))
                .andExpect(jsonPath("$.[0].expirationTime").value(result.getExpirationTime()))
                .andExpect(jsonPath("$.[0].isEnforceable").value(result.getIsEnforceable()));

    }

    @Test
    @DisplayName("Search in ticket types with lambda desc sort by description.")
    @Order(10)
    void searchTicketTypeLambdaDescSortByDescription() throws Exception {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = ticketTypeTestData.getTicketTypeSearchRequestModelSortByDescriptionDESC();
        TicketTypeModel result = ticketTypeTestData.getSingleTicketTypeModel();

        mvc.perform(post("/ticketType/searchLambda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTypeSearchRequestModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(result.getId().toString()))
                .andExpect(jsonPath("$.[0].name").value(result.getName()))
                .andExpect(jsonPath("$.[0].description").value(result.getDescription()))
                .andExpect(jsonPath("$.[0].price").value(result.getPrice()))
                .andExpect(jsonPath("$.[0].expirationTime").value(result.getExpirationTime()))
                .andExpect(jsonPath("$.[0].isEnforceable").value(result.getIsEnforceable()));

    }

    @Test
    @DisplayName("Update ticket type.")
    @Order(11)
    void updateTicketType() throws Exception {
        TicketTypeModel ticketTypeModel = ticketTypeTestData.getModifiedSingleTicketTypeModel();

        mvc.perform(put("/ticketType")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTypeModel)))
                .andExpect(status().isNoContent());

        mvc.perform(get("/ticketType/{id}", SINGLE_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketTypeModel.getId().toString()))
                .andExpect(jsonPath("$.name").value(ticketTypeModel.getName()))
                .andExpect(jsonPath("$.description").value(ticketTypeModel.getDescription()))
                .andExpect(jsonPath("$.price").value(ticketTypeModel.getPrice()))
                .andExpect(jsonPath("$.expirationTime").value(ticketTypeModel.getExpirationTime()))
                .andExpect(jsonPath("$.isEnforceable").value(ticketTypeModel.getIsEnforceable()));
    }

    @Test
    @DisplayName("Delete single ticket.")
    @Order(12)
    void deleteTicketTypeById() throws Exception {
        mvc.perform(delete("/ticketType/{id}", SINGLE_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mvc.perform(get("/ticketType/{id}", SINGLE_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete non existing single ticket.")
    @Order(13)
    void deleteTicketTypeByIdNonExistingTicket() throws Exception {
        mvc.perform(delete("/ticketType/{id}", NON_EXISTING_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TICKET_TYPE001"))
                .andExpect(jsonPath("$.message").value("Ticket type not found!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket type not found by id in database!"));
    }

    @Test
    @DisplayName("Get non existing ticket by id.")
    @Order(14)
    void getTicketTypeByIdNonExisting() throws Exception {
        mvc.perform(get("/ticketType/{id}", NON_EXISTING_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TICKET_TYPE001"))
                .andExpect(jsonPath("$.message").value("Ticket type not found!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket type not found by id in database!"));
    }

    @Test
    @DisplayName("Update non existing ticket type.")
    @Order(15)
    void updateTicketTypeNonExisting() throws Exception {
        TicketTypeModel ticketTypeModel = ticketTypeTestData.getNonExistingTicketTypeModel();

        mvc.perform(put("/ticketType")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ticketTypeModel)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TICKET_TYPE001"))
                .andExpect(jsonPath("$.message").value("Ticket type not found!"))
                .andExpect(jsonPath("$.detailedMessage").value("Ticket type not found by id in database!"));

    }

    @Test
    @DisplayName("Search in ticket types witn enforceable.")
    @Order(16)
    void searchTicketTypeWithEnforceable() throws Exception {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = ticketTypeTestData.getTicketTypeSearchRequestModelSortByNameASCEnforceable();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/ticketType/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(ticketTypeSearchRequestModel)))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "0"));
    }
}