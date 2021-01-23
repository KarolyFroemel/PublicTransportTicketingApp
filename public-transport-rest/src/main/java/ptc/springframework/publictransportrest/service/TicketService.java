package ptc.springframework.publictransportrest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.exception.TicketTypeNotFoundException;
import ptc.springframework.publictransportrest.exception.UserNotfoundException;
import ptc.springframework.publictransportrest.helper.DateTimeFormatterHelper;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.repository.TicketRepository;
import ptc.springframework.publictransportrest.repository.TicketTypeRepository;
import ptc.springframework.publictransportrest.repository.UserRepository;

import javax.transaction.Transactional;
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

    public List<TicketType> getTicketTypes() {
        log.info("Find all ticket types in db!");
        return ticketTypeRepository.findAll();
    }

    public List<Ticket> getTicketsByUserId(UUID id) {
        log.info("Find all tickets by user id!" );
        return userRepository.findById(id).orElseThrow(UserNotfoundException::new).getTickets();
    }

    @Transactional
    public void purchaseTicket(UUID userId, String ticketName, String date) {
        log.info("Create new ticket: {} for user: {}!", ticketName, userId);
        User user = userRepository.findById(userId).orElseThrow(UserNotfoundException::new);
        TicketType ticketType = ticketTypeRepository.findByName(ticketName).orElseThrow(TicketTypeNotFoundException::new);

        Ticket newTicket = Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(ticketType)
                .purchaseDate(LocalDateTime.now())
                .validFrom(DateTimeFormatterHelper.parseStringToDateTime(date))
                .validTo(calculateValidTo(ticketType))
                .build();

        ticketRepository.save(newTicket);
        log.info("Ticket created!");

        user.deductFee(ticketType.getPrice());
        userRepository.save(user);

        log.info("Ticket fee deducted!");
    }

    private LocalDateTime calculateValidTo(TicketType ticketType) {
        return LocalDateTime.now().plusDays(ticketType.getExpirationTime());
    }
}
