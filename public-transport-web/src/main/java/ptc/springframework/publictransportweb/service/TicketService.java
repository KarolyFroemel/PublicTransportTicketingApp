package ptc.springframework.publictransportweb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ptc.springframework.publictransportweb.model.TicketType;

import java.util.List;

@Slf4j
@Service
public class TicketService {

    private WebClient client;

    public List<TicketType> getTicketTypes(){

        log.debug("Get ticket types from api started");

        client = WebClient.create("http://localhost:8081");

        List<TicketType> ticketTypes = this.client
                .get()
                .uri("/tickets").retrieve()
                .bodyToFlux(TicketType.class)
                .collectList()
                .block();


        log.debug("Get ticket types from api successful");

        return ticketTypes;
    }
}
