package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.TicketTypeApi;
import contract.ticket.model.TicketTypeModel;
import contract.ticket.model.TicketTypeSearchRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
        ticketTypeService.deleteTicketTypeById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<TicketTypeModel> getTicketTypeById(UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketTypeService.getTicketTypeById(id));
    }

    @Override
    public ResponseEntity<List<TicketTypeModel>> getTicketTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketTypeService.getTicketTypes());
    }

    @Override
    public ResponseEntity<List<TicketTypeModel>> searchTicketType(Long xPage,
                                                                  Long xSize,
                                                                  @Valid TicketTypeSearchRequestModel ticketTypeSearchRequestModel) {
        Page<TicketTypeModel> models = ticketTypeService.searchTicketType(
                xPage.intValue(),
                xSize.intValue(),
                ticketTypeSearchRequestModel);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Page", String.valueOf(xPage));
        responseHeaders.set("X-Size", String.valueOf(xSize));
        responseHeaders.set("X-Total-Pages", String.valueOf(models.getTotalPages()));
        responseHeaders.set("X-Total-Size", String.valueOf(models.getTotalElements()));
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).headers(responseHeaders).body((models.getContent()));
    }

    @Override
    public ResponseEntity<List<TicketTypeModel>> searchTicketTypeLambda(@Valid TicketTypeSearchRequestModel ticketTypeSearchRequestModel) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketTypeService.getTicketTypesLambda(ticketTypeSearchRequestModel));
    }

    @Override
    public ResponseEntity<Void> updateTicketType(@Valid TicketTypeModel ticketTypeModel) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
