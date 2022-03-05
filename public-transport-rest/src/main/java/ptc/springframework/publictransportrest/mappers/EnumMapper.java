package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.ServiceSearchModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.enums.ServiceType;

@Mapper(config = MapstructConfig.class)
public interface EnumMapper {
    ServiceType serviceSearchModelServiceTypeEnumToServiceTypeEnum(ServiceSearchModel.TypeEnum typeEnum);
}
