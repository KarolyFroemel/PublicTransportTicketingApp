package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.ServiceSearchModel;
import contract.ticket.model.TicketSearchModel;
import contract.ticket.model.UserHistorySearchModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.enums.HistoryType;
import ptc.springframework.publictransportrest.enums.ServiceType;
import ptc.springframework.publictransportrest.enums.TicketStatus;

@Mapper(config = MapstructConfig.class)
public interface EnumMapper {
    ServiceType serviceSearchModelServiceTypeEnumToServiceTypeEnum(ServiceSearchModel.TypeEnum typeEnum);
    TicketStatus ticketSearchModelTicketStatusToTicketEntityTicketStatus(TicketSearchModel.StatusEnum ticketStatus);
    HistoryType userHistorySearchModelHistoryTypeToHistoryType(UserHistorySearchModel.HistoryTypeEnum historyTypeEnum);
}
