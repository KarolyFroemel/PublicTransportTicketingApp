package ptc.springframework.publictransportweb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ptc.springframework.publictransportweb.helper.DateFormatHelper;
import ptc.springframework.publictransportweb.model.PurchaseTicketModel;
import ptc.springframework.publictransportweb.model.TicketModel;
import ptc.springframework.publictransportweb.model.TicketPurchaseInfoDTO;
import ptc.springframework.publictransportweb.model.TicketTypeModel;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TicketService {

    @Autowired
    private DateFormatHelper dateFormatHelper;

    private final String host = "http://localhost:8081";

    private WebClient client = WebClient.create(host) ;

    public List<TicketTypeModel> getTicketTypes(){
        log.info("Get ticket types from api started");

        List<TicketTypeModel> ticketTypes = this.client
                .get()
                .uri("/tickets")
                .retrieve()
                .bodyToFlux(TicketTypeModel.class)
                .collectList()
                .block();

        log.debug("Get ticket types from api successful");
        return ticketTypes;
    }

    public List<TicketModel> getUserTickets(UUID userId) {
        log.info("Get all tickets that belong to specific user");

        List<TicketModel> ticketList = this.client
                .get()
                .uri("/tickets/"+userId.toString())
                .retrieve()
                .bodyToFlux(TicketModel.class)
                .collectList()
                .block();

        log.debug("Get all tickets that belong to specific user from api successful");
        return ticketList;
    }

    public List<TicketModel> getUserTicketsToValidate(UUID userId) {
        log.info("Get all tickets that belong to specific user to validate");

        List<TicketModel> ticketList = this.client
                .get()
                .uri("/tickets/validate/"+userId.toString())
                .retrieve()
                .bodyToFlux(TicketModel.class)
                .collectList()
                .block();

        log.debug("Get all tickets that belong to specific user to validate from api successful");
        return ticketList;
    }

    public List<TicketModel> getUserTicketsToRefund(UUID userId) {
        log.info("Get all tickets that belong to specific user to validate");

        List<TicketModel> ticketList = this.client
                .get()
                .uri("/tickets/refund/"+userId.toString())
                .retrieve()
                .bodyToFlux(TicketModel.class)
                .collectList()
                .block();

        log.debug("Get all tickets that belong to specific user to validate from api successful");
        return ticketList;
    }

    public void purchaseTicket(TicketPurchaseInfoDTO ticketPurchaseDTO) throws WebClientResponseException {
        log.info("Purchase new ticket");
        PurchaseTicketModel purchaseTicketModel = PurchaseTicketModel
                .builder()
                .ticketName(ticketPurchaseDTO.getTicketTypeName())
                .validFrom(ticketPurchaseDTO.getTicketTypeName().equals("Single ticket") ? null : dateFormatHelper.dateFormatter(ticketPurchaseDTO.getValidFrom()))
                .userId(ticketPurchaseDTO.getUserId())
                .build();

        this.client.post()
                .uri("/tickets/purchaseTicket")
                .body(Mono.just(purchaseTicketModel), PurchaseTicketModel.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void validateTicket(UUID ticketId) {
        log.info("Validate ticket");

        this.client.put()
                .uri("/tickets/validateTicket/"+ticketId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void refundTicket(UUID userId, UUID ticketId) {
        log.info("Refund ticket");

        this.client.delete()
                .uri("/tickets/"+userId+"/deleteTicket/"+ticketId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
