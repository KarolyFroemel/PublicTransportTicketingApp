package ptc.springframework.publictransportrest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ptc.springframework.publictransportrest.exception.TicketTypeNotFoundException;
import ptc.springframework.publictransportrest.exception.UserNotfoundException;
import ptc.springframework.publictransportrest.helper.DateTimeFormatterHelper;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.repository.TicketRepository;
import ptc.springframework.publictransportrest.repository.TicketTypeRepository;
import ptc.springframework.publictransportrest.repository.UserRepository;
import ptc.springframework.publictransportrest.testdata.TicketTestData;
import ptc.springframework.publictransportrest.testdata.TicketTypeTestData;
import ptc.springframework.publictransportrest.testdata.UserTestData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketTypeRepository ticketTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketRepository ticketRepository;

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

    @Test
    void testGetTicketTypes() {
        //given
        User user = UserTestData.getDavidUser();
        TicketType ticketType = TicketTypeTestData.getSingleTicketType();
        Ticket ticket = TicketTestData.getSingleTicket(user);

        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        Mockito.when(ticketTypeRepository.findByName(any(String.class))).thenReturn(Optional.of(ticketType));
        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        //when
        System.out.println(DateTimeFormatterHelper.parseDate(LocalDate.now()));
        ticketService.purchaseTicket(user.getId(), "Singel ticket", DateTimeFormatterHelper.parseDate(LocalDate.now()));

        //then
        Mockito.verify(userRepository, times(1)).findById(any(UUID.class));
        Mockito.verify(ticketTypeRepository, times(1)).findByName(any(String.class));
        Mockito.verify(ticketRepository, times(1)).save(any(Ticket.class));
        Mockito.verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void whenUserNotFoundExceptionThrownGetTicketTypes() {
        //given
        Mockito.when(userRepository.findById(any(UUID.class))).thenThrow(UserNotfoundException.class);
        //then
        UserNotfoundException exception = assertThrows(UserNotfoundException.class, () ->
            ticketService.purchaseTicket(UUID.randomUUID(),
                    "Single ticket", DateTimeFormatterHelper.parseDate(LocalDate.now()))
        );
    }

    @Test
    void whenTicketTypeNotFoundExceptionThrownGetTicketTypes() {
        //given
        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(UserTestData.getDavidUser()));
        Mockito.when(ticketTypeRepository.findByName(any(String.class))).thenThrow(TicketTypeNotFoundException.class);
        //then
        TicketTypeNotFoundException exception = assertThrows(TicketTypeNotFoundException.class, () ->
            ticketService.purchaseTicket(UUID.randomUUID(),
                    "Single ticket", DateTimeFormatterHelper.parseDate(LocalDate.now()))
        );
    }
}