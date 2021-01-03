package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.TicketModel;
import contract.ticket.model.TicketTypeModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TicketMapper {
    List<TicketTypeModel> toTicketTypeModelList(List<TicketType> ticketTypes);

    default List<TicketModel> toTicketModelList(List<Ticket> tickets) {

        List<TicketModel> ticketModelList = new ArrayList<>();

        tickets.forEach(ticket -> {
            TicketModel ticketmodel = new TicketModel();
            ticketmodel.setId(ticket.getId());
            ticketmodel.setName(ticket.getTicketType().getName());
            ticketmodel.setPurchaseDate(ticket.getPurchaseDate().toString());
            ticketmodel.setCanBeUsed(ticket.getCanBeUsed().toString());
            ticketmodel.setValidationDate(ticket.getValidationDate() == null ? " unused " : ticket.getValidationDate().toString());
        });

        return ticketModelList;
    };
}
