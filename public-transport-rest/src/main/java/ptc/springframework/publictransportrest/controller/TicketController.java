package ptc.springframework.publictransportrest.controller;

import contract.ticket.api.TicketsApi;
import contract.ticket.model.TicketTypeModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicketController implements TicketsApi {

    @Override
    public ResponseEntity<List<TicketTypeModel>> getTicketTypes() {
        return null;
    }
}
