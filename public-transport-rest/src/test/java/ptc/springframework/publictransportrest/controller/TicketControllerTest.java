package ptc.springframework.publictransportrest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ptc.springframework.publictransportrest.PublicTransportRestApplication;
import ptc.springframework.publictransportrest.mapper.TicketMapper;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.service.TicketService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PublicTransportRestApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TicketService ticketService;

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    TicketController ticketController;

    List<TicketType> ticketTypesTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        ticketTypesTest = new ArrayList<>();

        ticketTypesTest.add(new TicketType(1L, "Single ticket", "Single ticket for one ride. Valid to 60 minutes from validation.", 360L));
        ticketTypesTest.add(new TicketType(2L, "Group of single tickets.", "This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation.", 3000L));

    }

    @Test
    void getTicketTypes() throws Exception {

        //given
        Mockito.lenient().when(ticketService.getTicketTypes()).thenReturn(ticketTypesTest);

        //when
        mockMvc.perform(get("/tickets").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Single ticket"))
                .andExpect(jsonPath("$.[0].description").value("Single ticket for one ride. Valid to 60 minutes from validation."))
                .andExpect(jsonPath("$.[0].price").value(360L))
                .andExpect(jsonPath("$.[1].name").value("Group of single tickets."))
                .andExpect(jsonPath("$.[1].description").value("This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation."))
                .andExpect(jsonPath("$.[1].price").value(3000L));


    }
}