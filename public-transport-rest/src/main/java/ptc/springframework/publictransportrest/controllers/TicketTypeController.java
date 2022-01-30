package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.TicketTypeApi;
import contract.ticket.model.TicketTypeModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ptc.springframework.publictransportrest.services.TicketTypeService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class TicketTypeController implements TicketTypeApi {

    private final TicketTypeService ticketTypeService;

    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @Override
    public ResponseEntity<TicketTypeModel> createNewTicketType(@Valid TicketTypeModel ticketTypeModel) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketTypeService.createNewTicketType(ticketTypeModel));
    }

    @Override
    public ResponseEntity<Void> deleteTicketTypeById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<TicketTypeModel> getTicketTypeById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<TicketTypeModel>> getTicketTypes() {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateTicketType(@Valid TicketTypeModel ticketTypeModel) {
        return null;
    }
}
