package ptc.springframework.publictransportrest.services;

import contract.ticket.model.TicketTypeModel;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.entities.TicketType;
import ptc.springframework.publictransportrest.mappers.TicketTypeMapper;
import ptc.springframework.publictransportrest.repositories.TicketTypeRepository;

import java.util.UUID;

@Service
public class TicketTypeService {

    private TicketTypeMapper ticketTypeMapper;

    private TicketTypeRepository ticketTypeRepository;

    private KeycloakService keycloakService;

    public TicketTypeService(TicketTypeMapper ticketTypeMapper,
                             TicketTypeRepository ticketTypeRepository,
                             KeycloakService keycloakService)
    {
        this.ticketTypeMapper = ticketTypeMapper;
        this.ticketTypeRepository = ticketTypeRepository;
        this.keycloakService = keycloakService;
    }

    //TODO funcions

    //TODO tests

    //TODO: error handling

    //TODO: refactoring error mapping and handling

    //TODO: tesztelni a hosszokat es egyeb megszoritasokat, manualisan nem kell teszttel

    //TODO: kiszedni a current user id jat a kapott tokenbol

    public TicketTypeModel createNewTicketType(TicketTypeModel ticketTypeModel) {
        TicketType ticketType = ticketTypeMapper.ticketTypeModelToTicketTypeEntity(ticketTypeModel);
        ticketType.setId(UUID.randomUUID());
//        ticketType.setCreatedBy(keycloakService.getUserId());
//        ticketType.setCreatedOn(LocalDateTime.now());
        ticketType = ticketTypeRepository.save(ticketType);
        return ticketTypeMapper.ticketTypeEntityToTicketTypeModel(ticketType);
    }
}
