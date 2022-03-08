package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.TicketApi;
import contract.ticket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ptc.springframework.publictransportrest.services.TicketService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class TicketController implements TicketApi {

    @Autowired
    private TicketService ticketService;

    @Override
    public ResponseEntity<Void> purchaseTicket(@Valid @RequestBody PurchaseTicketModel purchaseTicketModel) {
        ticketService.purchaseTicket(purchaseTicketModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> refundTicket(UUID ticketId) {
        ticketService.refund(ticketId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<TicketHistoryModel>> searchInTicketHistory(Integer xPage, Integer xSize, @Valid TicketHistorySearchModel ticketHistorySearchModel) {
        return null;
    }

    @Override
    public ResponseEntity<List<TicketModel>> searchTicket(Integer xPage, Integer xSize, @Valid TicketSearchModel ticketSearchModel) {
//        ticketService.updateTicketStatus();
        Page<TicketModel> page = ticketService.searchTicket(
                xPage,
                xSize,
                ticketSearchModel);

        return ControllerHelper.buildPartialResponse(xPage, xSize, page);
    }

    @Override
    public ResponseEntity<Void> validateTicket(UUID ticketId) {
        ticketService.validateTicket(ticketId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
