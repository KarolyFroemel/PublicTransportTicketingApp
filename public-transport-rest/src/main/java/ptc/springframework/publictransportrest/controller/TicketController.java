package ptc.springframework.publictransportrest.controller;

import contract.ticket.api.TicketsApi;
import contract.ticket.model.PurchaseTicketModel;
import contract.ticket.model.TicketModel;
import contract.ticket.model.TicketTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ptc.springframework.publictransportrest.mapper.TicketMapper;
import ptc.springframework.publictransportrest.service.TicketService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class TicketController implements TicketsApi {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public ResponseEntity<List<TicketTypeModel>> getTicketTypes() {
        return ResponseEntity.ok(ticketMapper.toTicketTypeModelList(ticketService.getTicketTypes()));
    }

    @Override
    public ResponseEntity<List<TicketModel>> getUserTicketsById(UUID userId) {
        return ResponseEntity.ok(ticketMapper.toTicketModelList(ticketService.getTicketsByUserId(userId)));
    }

    @Override
    public ResponseEntity<Void> purchaseTicket(@Valid PurchaseTicketModel purchaseTicketModel) {
        ticketService.purchaseTicket(
                purchaseTicketModel.getUserId(),
                purchaseTicketModel.getTicketName(),
                purchaseTicketModel.getValidFrom());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
