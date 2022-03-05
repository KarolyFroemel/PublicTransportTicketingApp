package ptc.springframework.publictransportrest.services;

import contract.ticket.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.entities.Station;
import ptc.springframework.publictransportrest.entities.Station_;
import ptc.springframework.publictransportrest.exceptions.StationException;
import ptc.springframework.publictransportrest.keycloak.KeycloakService;
import ptc.springframework.publictransportrest.mappers.StationMapper;
import ptc.springframework.publictransportrest.repositories.StationRepository;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ptc.springframework.publictransportrest.exceptions.error.StationErrorCode.STATION_NOT_FOUND;

@Service
public class StationService {

    private final StationMapper stationMapper;

    private final StationRepository stationRepository;

    private final KeycloakService keycloakService;

    public StationModel createNewStation(CreateStationModel createStationModel) {
        Station station = stationMapper.createStationModelToStationEntity(createStationModel);
        station.setCreatedBy(keycloakService.getUserId());
        station.setCreatedOn(LocalDateTime.now());
        return stationMapper.stationEntityToStationModel(stationRepository.save(station));
    }

    public void deleteStationById(UUID id) {
        if(!stationRepository.existsById(id)) {
            throw new StationException(
                    STATION_NOT_FOUND,
                    "Station not found.",
                    "Station not found in database",
                    HttpStatus.NOT_FOUND
            );
        }

        stationRepository.deleteById(id);
    }

    public StationService(StationMapper stationMapper,
                          StationRepository stationRepository, KeycloakService keycloakService) {
        this.stationMapper = stationMapper;
        this.stationRepository = stationRepository;
        this.keycloakService = keycloakService;
    }

    public StationModel getStationModelById(UUID id) {
        return stationMapper.stationEntityToStationModel(getStationById(id));
    }

    public Station getStationById(UUID id) {
        return stationRepository.findById(id).orElseThrow(
                () -> new StationException(
                        STATION_NOT_FOUND,
                        "Station not found.",
                        "Station not found in database",
                        HttpStatus.NOT_FOUND
                )
        );
    }

    public void updateStation(StationModel stationModel) {
        if(!stationRepository.existsById(stationModel.getId())) {
            throw new StationException(
                    STATION_NOT_FOUND,
                    "Station not found.",
                    "Station not found in database",
                    HttpStatus.NOT_FOUND
            );
        }

        stationRepository.save(stationMapper.stationModelToStationEntity(stationModel));
    }

    public Page<StationModel> searchStation(final int pageNumber,
                                            final int pageSize,
                                            final StationSearchModel stationSearchModel) {

        Pageable pageable;

        if("ASC".equals(stationSearchModel.getSortOrder().getValue())) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(stationSearchModel.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(stationSearchModel.getSortBy()).descending());
        }


        Page<Station> servicePage = stationRepository.findAll(
                createSearchSpecification(stationSearchModel),
                pageable
        );

        return servicePage.map(this.stationMapper::stationEntityToStationModel);
    }

    private Specification<Station> createSearchSpecification(StationSearchModel stationSearchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (stationSearchModel.getName() != null && !stationSearchModel.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        root.get(Station_.name), "%" + stationSearchModel.getName() + "%"));
            }

            if(predicates.isEmpty()) {
                predicates.add(criteriaBuilder.conjunction());
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
