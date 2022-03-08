package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.PurchaseTicketModel;
import contract.ticket.model.TicketModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.entities.Ticket;

import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface TicketMapper {

    @Mappings({
            @Mapping(target = "name", source = "ticketType.name")
    })
    TicketModel ticketEntityToTicketModel(Ticket ticket);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "ticketType", ignore = true),
            @Mapping(target = "purchaseDate", ignore = true),
            @Mapping(target = "validTo", ignore = true),
            @Mapping(target = "validationTime", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    Ticket purchaseTicketModelToTicketEntity(PurchaseTicketModel purchaseTicketModel);

    List<TicketModel> ticketEntityListToTicketModelList(List<Ticket> ticketList);
}
