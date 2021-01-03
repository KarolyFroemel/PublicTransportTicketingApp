package ptc.springframework.publictransportrest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.repository.TicketTypeRepository;
import ptc.springframework.publictransportrest.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketTypeRepository ticketTypeRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void getTicketTypes() {
        //given
        List<TicketType> ticketTypes = new ArrayList<>();

        ticketTypes.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Single ticket")
                .description("Single ticket for one ride. Valid to 60 minutes from validation.")
                .price(360L).build());

        ticketTypes.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Group of single tickets.")
                .description("This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation.")
                .price(3000L).build());

        ticketTypes.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Monthly pass")
                .description("Montly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase.")
                .price(10000L).build());

        ticketTypes.add(TicketType.builder()
                .id(UUID.randomUUID())
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

    @Test
    void getTicketsByUserId() {

        //given
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Edinson Cavani")
                .email("edinson.cavani@gmail.com")
                .balance(1000000L)
                .build();


        TicketType ticketType = TicketType.builder()
                .id(UUID.randomUUID())
                .name("Single ticket")
                .description("Single ticket for one ride. Valid to 60 minutes from validation.")
                .price(360L).build();



        List<Ticket> tickets = new ArrayList<>();

        tickets.add(Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(ticketType)
                .purchaseDate(LocalDateTime.of(2020,
                        12,
                        31,
                        12,
                        0,
                        0))
                .canBeUsed(LocalDateTime.of(2021,
                        12,
                        31,
                        12,
                        0,
                        0)).build()
        );

        user.setTickets(tickets);

        Optional<User> otionalUserResult = Optional.of(user);

        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(otionalUserResult);

        //when
        List<Ticket> ticketResultList = userRepository.findById(UUID.randomUUID()).get().getTickets();

        //then
        assertEquals(1, ticketResultList.size());

        Mockito.verify(userRepository, times(1)).findById(any(UUID.class));

    }
}