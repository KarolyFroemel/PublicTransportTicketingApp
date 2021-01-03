package ptc.springframework.publictransportrest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ptc.springframework.publictransportrest.PublicTransportRestApplication;
import ptc.springframework.publictransportrest.mapper.TicketMapper;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.service.TicketService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PublicTransportRestApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    TicketService ticketService;

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    TicketController ticketController;

    private List<TicketType> ticketTypesTest;

    private List<Ticket> userTickets;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        ticketTypesTest = new ArrayList<>();

        ticketTypesTest.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Single ticket")
                .description("Single ticket for one ride. Valid to 60 minutes from validation.")
                .price(360L).build());

        ticketTypesTest.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Group of single tickets.")
                .description("This group contains 10 single tickets. " +
                        "Each of single ticket for one ride. Valid to 60 minutes from validation.")
                .price( 3000L).build());


    }

    @Test
    void getTicketTypes() throws Exception {

        //given
        Mockito.lenient().when(ticketService.getTicketTypes()).thenReturn(ticketTypesTest);

        //when+then
        mockMvc.perform(get("/tickets").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Single ticket"))
                .andExpect(jsonPath("$.[0].description").value("Single ticket for one ride. Valid to 60 minutes from validation."))
                .andExpect(jsonPath("$.[0].price").value(360L))
                .andExpect(jsonPath("$.[1].name").value("Group of single tickets."))
                .andExpect(jsonPath("$.[1].description").value("This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation."))
                .andExpect(jsonPath("$.[1].price").value(3000L));
    }

    @Test
    void getUserTicketsById() throws Exception {
        //given
//        userTickets = new ArrayList<>();
//
//        UUID userId = UUID.randomUUID();
//
//        TicketType ticketType = TicketTypeTestData.getOneSingleTicketType();
//
//        User user =
//
//        Ticket ticket = Ticket.builder()
//                .id(UUID.randomUUID())
//                .user(user)
//                .ticketType(ticketType)
//                .purchaseDate(LocalDateTime.of(2020,
//                        12,
//                        31,
//                        12,
//                        0,
//                        0))
//                .canBeUsed(LocalDateTime.of(2021,
//                        12,
//                        31,
//                        12,
//                        0,
//                        0))
//                .build();
//
//        userTickets.add(ticket);
//
//        Mockito.when(ticketService.getTicketsByUserId(userId)).thenReturn(userTickets);
//
//        //when+then
//        String url = "/tickets/"+userId;
//
//        List<Ticket> ddd = ticketService.getTicketsByUserId(userId);

//        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.[0].id").value(ticket.getId()))
//                .andExpect(jsonPath("$.[0].name").value(ticket.getTicketType().getName()))
//                .andExpect(jsonPath("$.[0].purchaseDate").value(ticket.getPurchaseDate().toString()))
//                .andExpect(jsonPath("$.[0].validationDate").value(" unused "))
//                .andExpect(jsonPath("$.[0].canBeUsed").value(ticket.getCanBeUsed().toString()));

//        MvcResult mvcResult = this.mockMvc.perform(get(url))
//                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.message").value("Hello World!!!"))
//                .andReturn();
    }
}