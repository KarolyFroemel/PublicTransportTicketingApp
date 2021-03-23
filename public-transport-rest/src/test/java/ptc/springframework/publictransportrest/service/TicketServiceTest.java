package ptc.springframework.publictransportrest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ptc.springframework.publictransportrest.exception.*;
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
import java.time.LocalDateTime;
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

    @Mock
    private AccountService accountService;

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
        List<Ticket> ticketResultList = ticketService.getTicketsByUserId(UUID.randomUUID());

        //then
        assertEquals(1, ticketResultList.size());
        Mockito.verify(userRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void purchaseTicket() {
        //given
        User user = UserTestData.getDavidUser();
        TicketType ticketType = TicketTypeTestData.getSingleTicketType();
        Ticket ticket = TicketTestData.getSingleTicket(user);

        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        Mockito.when(ticketTypeRepository.findByName(any(String.class))).thenReturn(Optional.of(ticketType));
        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        //when
        ticketService.purchaseTicket(user.getId(), "Singel ticket", DateTimeFormatterHelper.parseDate(LocalDate.now()));

        //then
        Mockito.verify(userRepository, times(1)).findById(any(UUID.class));
        Mockito.verify(ticketTypeRepository, times(1)).findByName(any(String.class));
        Mockito.verify(ticketRepository, times(1)).save(any(Ticket.class));
        Mockito.verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void whenUserNotFoundExceptionThrownInPurchaseTicket() {
        //given
        Mockito.when(userRepository.findById(any(UUID.class))).thenThrow(UserNotfoundException.class);
        //then
        UserNotfoundException exception = assertThrows(UserNotfoundException.class, () ->
            ticketService.purchaseTicket(UUID.randomUUID(),
                    "Single ticket", DateTimeFormatterHelper.parseDate(LocalDate.now()))
        );
    }

    @Test
    void whenTicketTypeNotFoundExceptionThrownInPurchaseTicket() {
        //given
        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(UserTestData.getDavidUser()));
        Mockito.when(ticketTypeRepository.findByName(any(String.class))).thenThrow(TicketTypeNotFoundException.class);
        //then
        TicketTypeNotFoundException exception = assertThrows(TicketTypeNotFoundException.class, () ->
            ticketService.purchaseTicket(UUID.randomUUID(),
                    "Single ticket", DateTimeFormatterHelper.parseDate(LocalDate.now()))
        );
    }

    @Test
    void deletTicketOrPass() {
        //given
        User user = UserTestData.getEdisonUser();
        Mockito.when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(user.getTickets().get(0)));
        Mockito.doNothing().when(accountService).addToBalance(any(UUID.class), any(Long.class));
        Mockito.doNothing().when(ticketRepository).delete(any(Ticket.class));

        //when
        ticketService.deleteTicket(UUID.randomUUID(), UUID.randomUUID());

        //then
        Mockito.verify(ticketRepository, times(1)).findById(any(UUID.class));
        Mockito.verify(accountService, times(1)).addToBalance(any(UUID.class), any(Long.class));
        Mockito.verify(ticketRepository, times(1)).delete(any(Ticket.class));
    }

    @Test
    void deleteAlreadyValidatedTicket() {
        //given
        User user = UserTestData.getEdisonUser();
        Ticket ticket = user.getTickets().get(0);
        ticket.setValidationDate(LocalDateTime.now());
        Mockito.when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));

        //then
        TicketAlreadyValidatedException exception = assertThrows(TicketAlreadyValidatedException.class, () ->
                ticketService.deleteTicket(UUID.randomUUID(), UUID.randomUUID()));
        Mockito.verify(ticketRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deleteExpiredTicket() {
        //given
        User user = UserTestData.getEdisonUser();
        Ticket ticket = user.getTickets().get(0);
        ticket.setValidTo(LocalDateTime.now().minusDays(1L));
        Mockito.when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));

        //then
        TicketExpiredException exception = assertThrows(TicketExpiredException.class, () ->
                ticketService.deleteTicket(UUID.randomUUID(), UUID.randomUUID()));
        Mockito.verify(ticketRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deleteValidationStartedPass() {
        //given
        User user = UserTestData.getEdisonUser();
        Ticket ticket = user.getTickets().get(1);
        ticket.setValidFrom(LocalDateTime.now().minusDays(1L));
        Mockito.when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));

        //then
        TicketExpiredException exception = assertThrows(TicketExpiredException.class, () ->
                ticketService.deleteTicket(UUID.randomUUID(), UUID.randomUUID()));
        Mockito.verify(ticketRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void validateTicket() {
        //given
        User user = UserTestData.getEdisonUser();
        Ticket ticket = user.getTickets().get(0);
        Mockito.when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));
        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        //when
        ticketService.validateTicket(UUID.randomUUID());

        //then
        Mockito.verify(ticketRepository, times(1)).findById(any(UUID.class));
        Mockito.verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void validateTicketCantValidatePassException() {
        //given
        User user = UserTestData.getEdisonUser();
        Ticket ticket = user.getTickets().get(1);
        Mockito.when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));

        //when+then
        CantValidatePassException exception = assertThrows(CantValidatePassException.class, () ->
                ticketService.validateTicket(UUID.randomUUID()));
        Mockito.verify(ticketRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void validateTicketTicketAlreadyValidatedException() {
        //given
        User user = UserTestData.getEdisonUser();
        Ticket ticket = user.getTickets().get(0);
        ticket.setValidationDate(LocalDateTime.now());
        Mockito.when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));

        //when+then
        TicketAlreadyValidatedException exception = assertThrows(TicketAlreadyValidatedException.class, () ->
                ticketService.validateTicket(UUID.randomUUID()));
        Mockito.verify(ticketRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void validateTicketTicketExpiredException() {
        //given
        User user = UserTestData.getEdisonUser();
        Ticket ticket = user.getTickets().get(0);
        ticket.setValidTo(LocalDateTime.now().minusDays(1));
        Mockito.when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));

        //when+then
        TicketExpiredException exception = assertThrows(TicketExpiredException.class, () ->
                ticketService.validateTicket(UUID.randomUUID()));
        Mockito.verify(ticketRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void getTicketsByUserIdToRefund() {
        //given
        User user = UserTestData.getEdisonUser();
        List<Ticket> tickets = user.getTickets();
        tickets.add(TicketTestData.getValidatedSingleTicket(user));
        tickets.add(TicketTestData.getExpiredDailyPass(user));
        tickets.add(TicketTestData.getExpiredMonthlyPass(user));
        Optional<User> otionalUserResult = Optional.of(user);
        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(otionalUserResult);

        //when
        List<Ticket> ticketResultList = ticketService.getTicketsByUserIdToRefund(UUID.randomUUID());

        //then
        assertEquals(6, user.getTickets().size());
        assertEquals(3, ticketResultList.size());
        Mockito.verify(userRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void getTicketsByUserIdToValidate() {
        //given
        User user = UserTestData.getEdisonUser();
        List<Ticket> tickets = user.getTickets();
        tickets.add(TicketTestData.getValidatedSingleTicket(user));
        Optional<User> otionalUserResult = Optional.of(user);
        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(otionalUserResult);

        //when
        List<Ticket> ticketResultList = ticketService.getTicketsByUserIdToValidate(UUID.randomUUID());

        //then
        assertEquals(4, user.getTickets().size());
        assertEquals(1, ticketResultList.size());
        Mockito.verify(userRepository, times(1)).findById(any(UUID.class));
    }
}