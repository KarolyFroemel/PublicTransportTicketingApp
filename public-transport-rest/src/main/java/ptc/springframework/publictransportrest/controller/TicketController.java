package ptc.springframework.publictransportrest.controller;

import contract.ticket.api.TicketsApi;
import contract.ticket.model.TicketTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ptc.springframework.publictransportrest.mapper.TicketMapper;
import ptc.springframework.publictransportrest.service.TicketService;

import java.util.List;

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
}
