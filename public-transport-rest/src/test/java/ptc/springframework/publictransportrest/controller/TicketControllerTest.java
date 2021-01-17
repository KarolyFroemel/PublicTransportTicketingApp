package ptc.springframework.publictransportrest.controller;

import contract.ticket.model.TicketModel;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import ptc.springframework.publictransportrest.PublicTransportRestApplication;
import ptc.springframework.publictransportrest.mapper.TicketMapper;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.service.TicketService;
import ptc.springframework.publictransportrest.testdata.TicketTestData;
import ptc.springframework.publictransportrest.testdata.TicketTypeTestData;
import ptc.springframework.publictransportrest.testdata.UserTestData;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PublicTransportRestApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@Transactional
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    TicketService ticketService;

    @Autowired
    TicketMapper ticketMapper;

    @Mock
    TicketMapper ticketMapperMock;

    @InjectMocks
    TicketController ticketController;

    private List<TicketType> ticketTypesTest;

    private List<Ticket> userTickets;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        ticketTypesTest = TicketTypeTestData.getTicketTypeList();
    }

    @Test
    void getTicketTypes() throws Exception {

        //given
        Mockito.lenient()
                .when(ticketMapperMock.toTicketTypeModelList(any()))
                .thenReturn(ticketMapper.toTicketTypeModelList(TicketTypeTestData.getTicketTypeList()));

//        when+then
        mockMvc.perform(get("/tickets").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Single ticket"))
                .andExpect(jsonPath("$.[0].description").value("Single ticket for one ride. Valid to 60 minutes from validation."))
                .andExpect(jsonPath("$.[0].price").value(360L))
                .andExpect(jsonPath("$.[0].expirationTime").value(366L))
                .andExpect(jsonPath("$.[1].name").value("Monthly pass"))
                .andExpect(jsonPath("$.[1].description").value("Monthly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase."))
                .andExpect(jsonPath("$.[1].price").value(10000L))
                .andExpect(jsonPath("$.[1].expirationTime").value(31L))
                .andExpect(jsonPath("$.[2].name").value("Daily pass"))
                .andExpect(jsonPath("$.[2].description").value("Daily pass to use any service (Bus, Tram, Underground, Ship). Valid only purchase day."))
                .andExpect(jsonPath("$.[2].price").value(1500L))
                .andExpect(jsonPath("$.[2].expirationTime").value(1L));

    }

    @Test
    void getUserTicketsById() throws Exception {
        //given

        User user = UserTestData.getDavidUser();

        userTickets = TicketTestData.getTicketList(user);

        List<TicketModel> ticketModelList = ticketMapper.toTicketModelList(userTickets);

        Mockito.when(ticketMapperMock.toTicketModelList(any())).thenReturn(ticketModelList);

        //when*then
        mockMvc.perform(get("/tickets/516ce0cc-4b70-11eb-ae93-0242ac130002").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(ticketModelList.get(0).getId().toString()))
                .andExpect(jsonPath("$.[0].name").value(ticketModelList.get(0).getName()))
                .andExpect(jsonPath("$.[0].purchaseDate").value(ticketModelList.get(0).getPurchaseDate()))
                .andExpect(jsonPath("$.[0].validationDate").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.[0].validFrom").value(ticketModelList.get(0).getValidFrom()))
                .andExpect(jsonPath("$.[0].validTo").value(ticketModelList.get(0).getValidTo()));
    }
}