package ptc.springframework.publictransportrest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.exception.*;
import ptc.springframework.publictransportrest.helper.DateTimeFormatterHelper;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.repository.TicketRepository;
import ptc.springframework.publictransportrest.repository.TicketTypeRepository;
import ptc.springframework.publictransportrest.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TicketService {

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    public List<TicketType> getTicketTypes() {
        log.info("Find all ticket types in db!");
        return ticketTypeRepository.findAll();
    }

    public List<Ticket> getTicketsByUserId(UUID id) throws UserNotfoundException {
        log.info("Find all tickets by user id!" );
        return userRepository.findById(id).orElseThrow(UserNotfoundException::new).getTickets();
    }

    @Transactional
    public void purchaseTicket(UUID userId, String ticketName, String validFrom) throws UserNotfoundException, TicketTypeNotFoundException {
        log.info("Create new ticket: {} for user: {}!", ticketName, userId);
        log.info("user:"+userId);
        log.info("ticket:"+ticketName);
        User user = userRepository.findById(userId).orElseThrow(UserNotfoundException::new);
        TicketType ticketType = ticketTypeRepository.findByName(ticketName).orElseThrow(TicketTypeNotFoundException::new);
        log.info("ticket type id: " + ticketType.getId());

        Ticket newTicket = Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(ticketType)
                .purchaseDate(LocalDateTime.now())
                .validFrom(calculateValidFrom(validFrom, ticketType))
                .validTo(calculateValidTo(validFrom, ticketType))
                .build();

        log.info("new ticket id:  " + newTicket.getId());

        ticketRepository.save(newTicket);
        log.info("Ticket created!");

        user.getAccount().deductFee(ticketType.getPrice());
        userRepository.save(user);

        log.info("Ticket fee deducted!");
    }

    @Transactional
    public void deleteTicket(UUID userId, UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(TicketNotFoundException::new);

        if((ticket.getTicketType().getName().equals("Monthly pass") ||
                ticket.getTicketType().getName().equals("Daily pass")) && ticket.isPassValidationStarted()) {
            throw new TicketExpiredException();
        } else if(ticket.getTicketType().getName().equals("Single ticket")) {
            if(ticket.isTicketValidated()) {
                throw new TicketAlreadyValidatedException();
            }

            if(ticket.isTicketExpired()) {
                throw new TicketExpiredException();
            }
        }

        accountService.addToBalance(userId, ticket.getTicketType().getPrice());

        ticketRepository.delete(ticket);
    }

    public void validateTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(TicketNotFoundException::new);

        if(ticket.getTicketType().getName().equals("Monthly pass") ||
                ticket.getTicketType().getName().equals("Daily pass")) {
            throw new CantValidatePassException();
        }

        if(ticket.isTicketValidated()) {
            throw new TicketAlreadyValidatedException();
        }

        if(ticket.isTicketExpired()) {
            throw new TicketExpiredException();
        }

        ticket.setValidationDate(LocalDateTime.now());

        ticketRepository.save(ticket);
    }

    private LocalDateTime calculateValidTo(String validFrom, TicketType ticketType) {

        LocalDateTime startDate = DateTimeFormatterHelper.parseStringToEndOfDate(validFrom);

        if("Single ticket".equals(ticketType.getName()) ||
                startDate.isBefore(LocalDateTime.now().minusMinutes(3))) {
            startDate = DateTimeFormatterHelper.parseStringToEndOfDate(LocalDate.now().toString());
        }

        if ("Daily pass".equals(ticketType.getName())) {
            return startDate;
        }

        return startDate.plusDays(ticketType.getExpirationTime());
    }

    private LocalDateTime calculateValidFrom(String validFrom, TicketType ticketType) {

        LocalDateTime startDate = DateTimeFormatterHelper.parseStringToStartOfDate(validFrom);

        if("Single ticket".equals(ticketType.getName()) ||
                startDate.isBefore(LocalDateTime.now().minusMinutes(3))) {
            startDate = DateTimeFormatterHelper.parseStringToStartOfDate(LocalDate.now().toString());
        }

        return startDate;
    }
}
