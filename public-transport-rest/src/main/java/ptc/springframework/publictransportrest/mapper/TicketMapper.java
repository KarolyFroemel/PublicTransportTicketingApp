package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.TicketModel;
import contract.ticket.model.TicketTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;

import java.util.List;

@Mapper
public interface TicketMapper {

    @Mappings({
            @Mapping(target="name", source="name"),
            @Mapping(target="description", source="description"),
            @Mapping(target="price", source="price")
    })
    TicketTypeModel toTicketTypeModel(TicketType ticketType);


    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="name", source="ticketType.name"),
            @Mapping(target="purchaseDate", source="purchaseDate", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target="validationDate", source="validationDate", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target="canBeUsed", source="canBeUsed", dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    TicketModel toTicketModel(Ticket ticket);

    List<TicketTypeModel> toTicketTypeModelList(List<TicketType> ticketTypes);

    List<TicketModel> toTicketModelList(List<Ticket> tickets);
}
