package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.StationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.entities.Station;

import java.util.List;
import java.util.Set;

@Mapper(config = MapstructConfig.class)
public interface StationMapper {

    @Mappings({
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "createdOn", ignore = true),
            @Mapping(target = "modifiedBy", ignore = true),
            @Mapping(target = "modifiedOn", ignore = true)
    })
    Station stationModelToStationEntity(StationModel stationModel);

    StationModel stationEntityToStationModel(Station station);

    List<StationModel> stationEntitySetToStationModelList(Set<Station> stations);
}
