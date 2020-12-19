package ptc.springframework.publictransportrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.Repository.TicketRepository;
import ptc.springframework.publictransportrest.model.TicketType;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    public List<TicketType> ticketTypes() {
        //TODO:ures lista eseten vegy eldobni egy hibat vagy ha ugyis hobat dob akkor egy global exception handlert epiteni
        return ticketRepository.findAll();
    }
}
