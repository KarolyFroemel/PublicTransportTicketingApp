package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.TicketTypeModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.model.TicketType;

import java.util.List;

@Mapper
public interface TicketMapper {
    List<TicketTypeModel> toTicketTypeModelList(List<TicketType> ticketTypes);
}
