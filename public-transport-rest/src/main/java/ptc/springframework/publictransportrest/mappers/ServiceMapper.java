package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.CreateServiceModel;
import contract.ticket.model.ServiceModel;
import contract.ticket.model.ServiceWithStationsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.entities.PTCService;

@Mapper(uses = {
        StationMapper.class
}, config = MapstructConfig.class)
public interface ServiceMapper {

    @Mappings({
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "createdOn", ignore = true),
            @Mapping(target = "modifiedBy", ignore = true),
            @Mapping(target = "modifiedOn", ignore = true),
            @Mapping(target = "stations", ignore = true)
    })
    PTCService serviceModelToServiceEntity(ServiceModel serviceModel);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "createdOn", ignore = true),
            @Mapping(target = "modifiedBy", ignore = true),
            @Mapping(target = "modifiedOn", ignore = true),
            @Mapping(target = "stations", ignore = true)
    })
    PTCService createServiceModelToServiceEntity(CreateServiceModel createServiceModel);

    ServiceModel serviceEntityToServiceModel(PTCService service);

    ServiceWithStationsModel serviceEntityToServiceWithStationsModel(PTCService service);


}
