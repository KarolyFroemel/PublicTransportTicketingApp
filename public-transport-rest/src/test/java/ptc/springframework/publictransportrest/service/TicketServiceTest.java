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
import ptc.springframework.publictransportrest.testdata.TicketTestData;
import ptc.springframework.publictransportrest.testdata.TicketTypeTestData;
import ptc.springframework.publictransportrest.testdata.UserTestData;

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
        List<TicketType> ticketTypes = TicketTypeTestData.getTicketTypeList();
        Mockito.lenient().when(ticketTypeRepository.findAll()).thenReturn(ticketTypes);

        //when
        List<TicketType> result = ticketService.getTicketTypes();

        //then
        assertEquals(3, result.size());
        Mockito.verify(ticketTypeRepository, times(1)).findAll();

    }

    @Test
    void getTicketsByUserId() {

        //given
        User user = UserTestData.getEdisonUser();

        List<Ticket> tickets = new ArrayList<>();

        tickets.add(TicketTestData.getSingleTicket(user));

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