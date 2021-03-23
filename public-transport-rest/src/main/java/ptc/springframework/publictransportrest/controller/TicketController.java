package ptc.springframework.publictransportrest.controller;

import contract.ticket.api.TicketsApi;
import contract.ticket.model.PurchaseTicketModel;
import contract.ticket.model.TicketModel;
import contract.ticket.model.TicketTypeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ptc.springframework.publictransportrest.mapper.TicketMapper;
import ptc.springframework.publictransportrest.service.TicketService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class TicketController implements TicketsApi {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public ResponseEntity<Void> deleteTicket(UUID userId, UUID ticketId) {
        log.info("Delete ticket endpoint called");
        ticketService.deleteTicket(userId, ticketId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<List<TicketTypeModel>> getTicketTypes() {
        log.info("Get ticket types endpoint called");
        return ResponseEntity.ok(ticketMapper.toTicketTypeModelList(ticketService.getTicketTypes()));
    }

    @Override
    public ResponseEntity<List<TicketModel>> getUserTicketsById(UUID userId) {
        log.info("User tickets endpoint called");
        return ResponseEntity.ok(ticketMapper.toTicketModelList(ticketService.getTicketsByUserId(userId)));
    }

    @Override
    public ResponseEntity<List<TicketModel>> getUserTicketsByIdToRefund(UUID userId) {
        log.info("User tickets to refund endpoint called");
        return ResponseEntity.ok(ticketMapper.toTicketModelList(ticketService.getTicketsByUserIdToRefund(userId)));
    }

    @Override
    public ResponseEntity<List<TicketModel>> getUserTicketsByIdToValidate(UUID userId) {
        log.info("User tickets to validate endpoint called");
        return ResponseEntity.ok(ticketMapper.toTicketModelList(ticketService.getTicketsByUserIdToValidate(userId)));
    }

    @Override
    public ResponseEntity<Void> purchaseTicket(@Valid PurchaseTicketModel purchaseTicketModel) {
        log.info("Purchase ticket endpoint called");
        ticketService.purchaseTicket(
                purchaseTicketModel.getUserId(),
                purchaseTicketModel.getTicketName(),
                purchaseTicketModel.getValidFrom());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> validateTicket(UUID ticketId) {
        log.info("Ticket validation endpoint called with ticketId:{}",ticketId);
        ticketService.validateTicket(ticketId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
