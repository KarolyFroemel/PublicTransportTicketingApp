package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.TicketTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.entities.TicketType;

import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface TicketTypeMapper {

    @Mappings({
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "createdOn", ignore = true),
            @Mapping(target = "modifiedBy", ignore = true),
            @Mapping(target = "modifiedOn", ignore = true),
            @Mapping(target = "tickets", ignore = true)
    })
    TicketType ticketTypeModelToTicketTypeEntity(TicketTypeModel ticketTypeModel);
    TicketTypeModel ticketTypeEntityToTicketTypeModel(TicketType ticketType);
    List<TicketTypeModel> ticketTypeEntityListToTicketTypeModelList(List<TicketType> ticketTypeList);
}
