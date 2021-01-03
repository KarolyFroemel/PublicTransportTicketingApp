package ptc.springframework.publictransportrest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.exception.UserNotfoundException;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.repository.TicketRepository;
import ptc.springframework.publictransportrest.repository.TicketTypeRepository;
import ptc.springframework.publictransportrest.repository.UserRepository;

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
}
