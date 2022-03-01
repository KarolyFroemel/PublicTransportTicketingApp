package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.TicketTypeApi;
import contract.ticket.model.TicketTypeModel;
import contract.ticket.model.TicketTypeSearchRequestModel;
import org.springframework.data.domain.Page;
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
        Page<TicketTypeModel> page = ticketTypeService.searchTicketType(
                xPage.intValue(),
                xSize.intValue(),
                ticketTypeSearchRequestModel);

        return ControllerHelper.buildPartialResponse(xPage, xSize, page);
    }

    @Override
    public ResponseEntity<List<TicketTypeModel>> searchTicketTypeLambda(@Valid TicketTypeSearchRequestModel ticketTypeSearchRequestModel) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketTypeService.getTicketTypesLambda(ticketTypeSearchRequestModel));
    }

    @Override
    public ResponseEntity<Void> updateTicketType(@Valid TicketTypeModel ticketTypeModel) {
        ticketTypeService.updateTicketType(ticketTypeModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
