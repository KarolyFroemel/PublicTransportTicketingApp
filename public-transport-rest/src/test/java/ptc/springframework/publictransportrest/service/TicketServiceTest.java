package ptc.springframework.publictransportrest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.repository.TicketTypeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;


class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketTypeRepository ticketTypeRepository;

    private List<TicketType> ticketTypes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTicketTypes() {
        //given
        ticketTypes = new ArrayList<>();

        ticketTypes.add(TicketType.builder()
                .id(1L)
                .name("Single ticket")
                .description("Single ticket for one ride. Valid to 60 minutes from validation.")
                .price(360L).build());

        ticketTypes.add(TicketType.builder()
                .id(2L)
                .name("Group of single tickets.")
                .description("This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation.")
                .price(3000L).build());

        ticketTypes.add(TicketType.builder()
                .id(3L)
                .name("Monthly pass")
                .description("Montly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase.")
                .price(10000L).build());

        ticketTypes.add(TicketType.builder()
                .id(4L)
                .name("Daily pass")
                .description("Daily pass to use any service (Bus, Tram, Underground, Ship). Valid only purchase day.")
                .price(1500L).build());

        Mockito.lenient().when(ticketTypeRepository.findAll()).thenReturn(ticketTypes);

        //when
        List<TicketType> result = ticketService.getTicketTypes();

        //then
        assertEquals(4, result.size());
        Mockito.verify(ticketTypeRepository, times(1)).findAll();

    }
}