package ptc.springframework.publictransportrest.services;

import contract.ticket.model.CreateServiceModel;
import contract.ticket.model.ServiceModel;
import contract.ticket.model.ServiceSearchModel;
import contract.ticket.model.ServiceWithStationsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.entities.*;
import ptc.springframework.publictransportrest.exceptions.ServiceException;
import ptc.springframework.publictransportrest.keycloak.KeycloakService;
import ptc.springframework.publictransportrest.mappers.EnumMapper;
import ptc.springframework.publictransportrest.mappers.ServiceMapper;
import ptc.springframework.publictransportrest.repositories.ServiceRepository;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ptc.springframework.publictransportrest.exceptions.error.ServiceErrorCode.SERVICE_NOT_FOUND;

@Service
@Transactional
public class PTCServiceService {

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    private final StationService stationService;

    private final EnumMapper enumMapper;

    private final KeycloakService keycloakService;


    public PTCServiceService(ServiceRepository serviceRepository,
                             ServiceMapper serviceMapper,
                             StationService stationService,
                             EnumMapper enumMapper,
                             KeycloakService keycloakService) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.stationService = stationService;
        this.enumMapper = enumMapper;
        this.keycloakService = keycloakService;
    }

    public ServiceModel createNewService(CreateServiceModel serviceModel) {
        PTCService service = serviceMapper.createServiceModelToServiceEntity(serviceModel);
        service.setCreatedBy(keycloakService.getUserId());
        service.setCreatedOn(LocalDateTime.now());
        return serviceMapper.serviceEntityToServiceModel(serviceRepository.save(service));
    }

    public void deleteServiceById(UUID id) {
        if (!serviceRepository.existsById(id)) {
            throw new ServiceException(
                    SERVICE_NOT_FOUND,
                    "Service not found.",
                    "Service not found in database.",
                    HttpStatus.NOT_FOUND
            );
        }

        serviceRepository.deleteById(id);
    }

    public ServiceWithStationsModel getServiceById(UUID id) {
        return serviceMapper.serviceEntityToServiceWithStationsModel(getService(id));
    }

    public void addStationToService(UUID serviceId, UUID stationId) {
        Station station = stationService.getStationById(stationId);

        PTCService service = getService(serviceId);

        service.getStations().add(station);
        serviceRepository.save(service);
    }

    public void removeStationFromService(UUID serviceId, UUID stationId) {
        PTCService service = getService(serviceId);

        service.getStations().removeIf(s -> s.getId().equals(stationId));

        serviceRepository.save(service);
    }

    public void updateService(ServiceModel serviceModel) {
        if(!serviceRepository.existsById(serviceModel.getId())) {
            throw new ServiceException(
                    SERVICE_NOT_FOUND,
                    "Service not found.",
                    "Service not found in database.",
                    HttpStatus.NOT_FOUND
            );
        }

        PTCService sourceService = serviceMapper.serviceModelToServiceEntity(serviceModel);
        PTCService targetService = getService(serviceModel.getId());

        targetService.setName(sourceService.getName());
        targetService.setType(sourceService.getType());
        targetService.setModifiedBy(keycloakService.getUserId());
        targetService.setModifiedOn(LocalDateTime.now());
        serviceRepository.save(targetService);
    }

    public Page<ServiceModel> searchService(final int pageNumber,
                                          final int pageSize,
                                          final ServiceSearchModel serviceSearchModel) {

        Pageable pageable;

        if("ASC".equals(serviceSearchModel.getSortOrder().getValue())) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(serviceSearchModel.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(serviceSearchModel.getSortBy()).descending());
        }


        Page<PTCService> servicePage = serviceRepository.findAll(
                createSearchSpecification(serviceSearchModel),
                pageable
        );

        return servicePage.map(this.serviceMapper::serviceEntityToServiceModel);
    }

    private Specification<PTCService> createSearchSpecification(ServiceSearchModel serviceSearchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (serviceSearchModel.getName() != null && !serviceSearchModel.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        root.get(PTCService_.name), "%" + serviceSearchModel.getName() + "%"));
            }

            if (serviceSearchModel.getType() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get(PTCService_.type), enumMapper.serviceSearchModelServiceTypeEnumToServiceTypeEnum(serviceSearchModel.getType())));
            }

            if(predicates.isEmpty()) {
                predicates.add(criteriaBuilder.conjunction());
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private PTCService getService(UUID serviceId) {
        return serviceRepository.findById(serviceId).orElseThrow(
                () -> new ServiceException(
                        SERVICE_NOT_FOUND,
                        "Service not found.",
                        "Service not found in database.",
                        HttpStatus.NOT_FOUND
                )
        );
    }
}
