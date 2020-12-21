package ptc.springframework.publictransportrest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;


class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    private List<TicketType> ticketTypes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTicketTypes() {
        //given
        ticketTypes = new ArrayList<>();
        ticketTypes.add(new TicketType(1L, "Single ticket", "Single ticket for one ride. Valid to 60 minutes from validation.", 360L));
        ticketTypes.add(new TicketType(2L, "Group of single tickets.", "This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation.", 3000L));
        ticketTypes.add(new TicketType(3L, "Monthly pass", "Montly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase.", 10000L));
        ticketTypes.add(new TicketType(4L, "Daily pass", "Daily pass to use any service (Bus, Tram, Underground, Ship). Valid only purchase day.", 1500L));

        Mockito.lenient().when(ticketRepository.findAll()).thenReturn(ticketTypes);

        //when
        List<TicketType> result = ticketService.getTicketTypes();

        //then
        assertEquals(4, result.size());
        Mockito.verify(ticketRepository, times(1)).findAll();

    }
}