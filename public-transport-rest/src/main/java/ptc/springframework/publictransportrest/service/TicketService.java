package ptc.springframework.publictransportrest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.repository.TicketRepository;

import java.util.List;

@Service
@Slf4j
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    public List<TicketType> getTicketTypes() {
        log.debug("find all ticket types in db!");
        return ticketRepository.findAll();
    }
}
