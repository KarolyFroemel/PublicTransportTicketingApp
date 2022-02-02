package ptc.springframework.publictransportrest.services;

import contract.ticket.model.TicketTypeModel;
import contract.ticket.model.TicketTypeSearchRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.entities.TicketType;
import ptc.springframework.publictransportrest.entities.TicketType_;
import ptc.springframework.publictransportrest.exceptions.TicketTypeExeption;
import ptc.springframework.publictransportrest.mappers.TicketTypeMapper;
import ptc.springframework.publictransportrest.repositories.TicketTypeRepository;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ptc.springframework.publictransportrest.exceptions.error.TicketTypeErrorCode.TICKET_TYPE_NOT_FOUND;

@Service
public class TicketTypeService {

    private final TicketTypeMapper ticketTypeMapper;

    private final TicketTypeRepository ticketTypeRepository;

//    private final KeycloakService keycloakService;

    public TicketTypeService(TicketTypeMapper ticketTypeMapper,
                             TicketTypeRepository ticketTypeRepository
                             /*KeycloakService keycloakService*/)
    {
        this.ticketTypeMapper = ticketTypeMapper;
        this.ticketTypeRepository = ticketTypeRepository;
        /*this.keycloakService = keycloakService;*/
    }

    public TicketTypeModel createNewTicketType(TicketTypeModel ticketTypeModel) {
        TicketType ticketType = ticketTypeMapper.ticketTypeModelToTicketTypeEntity(ticketTypeModel);
        ticketType.setCreatedBy(/*keycloakService.getUserId()*/null);
        ticketType.setCreatedOn(LocalDateTime.now());
        ticketType = ticketTypeRepository.save(ticketType);
        return ticketTypeMapper.ticketTypeEntityToTicketTypeModel(ticketType);
    }

    public void  deleteTicketTypeById(UUID id) {
        if(ticketTypeRepository.existsById(id)) {
            ticketTypeRepository.deleteById(id);
        } else {
            throw new TicketTypeExeption(TICKET_TYPE_NOT_FOUND,
                    "Ticket type not found!",
                    "Ticket type not found by id in database!",
                    HttpStatus.NOT_FOUND);
        }
    }

    public TicketTypeModel getTicketTypeById(UUID id) {
        return ticketTypeMapper.ticketTypeEntityToTicketTypeModel(ticketTypeRepository.findById(id).orElseThrow(
                () -> new TicketTypeExeption(
                        TICKET_TYPE_NOT_FOUND,
                        "Ticket type not found!",
                        "Ticket type not found by id in database!",
                        HttpStatus.NOT_FOUND
                )
        ));
    }

    public List<TicketTypeModel> getTicketTypes(){
       return ticketTypeMapper.ticketTypeEntityListToTicketTypeModelList(
         ticketTypeRepository.findAll()
       );
    }

    public void updateTicketType(TicketTypeModel ticketTypeModel) {
        TicketType ticketType = ticketTypeMapper.ticketTypeModelToTicketTypeEntity(ticketTypeModel);
        if(ticketTypeRepository.existsById(ticketType.getId())) {
            ticketTypeRepository.save(ticketType);
        } else {
            throw new TicketTypeExeption(TICKET_TYPE_NOT_FOUND,
                    "Ticket type not found!",
                    "Ticket type not found by id in database!",
                    HttpStatus.NOT_FOUND);
        }
    }

    public Page<TicketTypeModel> searchTicketType(final int pageNumber, final int pageSize, final TicketTypeSearchRequestModel ticketTypeSearchRequestModel) {

        Pageable pageable;

        if("ASC".equals(ticketTypeSearchRequestModel.getSortOrder().getValue())) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(ticketTypeSearchRequestModel.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(ticketTypeSearchRequestModel.getSortBy()).descending());
        }


        Page<TicketType> ticketTypePage = ticketTypeRepository.findAll(
                createSearchSpecification(ticketTypeSearchRequestModel),
                pageable
        );

        return ticketTypePage.map(this.ticketTypeMapper::ticketTypeEntityToTicketTypeModel);
    }

    private Specification<TicketType> createSearchSpecification(TicketTypeSearchRequestModel ticketTypeSearchRequestModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ticketTypeSearchRequestModel.getName() != null) {
                predicates.add(criteriaBuilder.like(
                        root.get(TicketType_.name), "%" + ticketTypeSearchRequestModel.getName() + "%"));
            }

            if (ticketTypeSearchRequestModel.getDescription() != null) {
                predicates.add(criteriaBuilder.like(
                        root.get(TicketType_.description), "%" + ticketTypeSearchRequestModel.getDescription() + "%"));
            }

            if(ticketTypeSearchRequestModel.getName() == null && ticketTypeSearchRequestModel.getDescription() == null) {
                predicates.add(criteriaBuilder.conjunction());
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public List<TicketTypeModel> getTicketTypesLambda(TicketTypeSearchRequestModel ticketTypeSearchRequestModel) {
        List<TicketType> ticketTypeList = ticketTypeRepository.findAll();

        if(ticketTypeSearchRequestModel.getName() != null && !ticketTypeSearchRequestModel.getName().isBlank()) {

            ticketTypeList = ticketTypeList
                    .stream()
                    .filter(item -> item.getName().contains(ticketTypeSearchRequestModel.getName()))
                    .collect(Collectors.toList());
        }

        if(ticketTypeSearchRequestModel.getDescription() != null && !ticketTypeSearchRequestModel.getDescription().isBlank()) {

            ticketTypeList = ticketTypeList
                    .stream()
                    .filter(item -> item.getDescription().contains(ticketTypeSearchRequestModel.getDescription()))
                    .collect(Collectors.toList());
        }


        if (ticketTypeSearchRequestModel.getSortBy().equals("name")) {

            if (ticketTypeSearchRequestModel.getSortOrder().getValue().equals("ASC")) {
                ticketTypeList = ticketTypeList
                        .stream()
                        .sorted(Comparator.comparing(TicketType::getName))
                        .collect(Collectors.toList());
            } else {
                ticketTypeList = ticketTypeList
                        .stream()
                        .sorted(Comparator.comparing(TicketType::getName).reversed())
                        .collect(Collectors.toList());
            }

        } else {

            if (ticketTypeSearchRequestModel.getSortOrder().getValue().equals("ASC")) {
                ticketTypeList = ticketTypeList
                        .stream()
                        .sorted(Comparator.comparing(TicketType::getDescription))
                        .collect(Collectors.toList());
            } else {
                ticketTypeList = ticketTypeList
                        .stream()
                        .sorted(Comparator.comparing(TicketType::getDescription).reversed())
                        .collect(Collectors.toList());
            }
        }


        return ticketTypeMapper.ticketTypeEntityListToTicketTypeModelList(ticketTypeList);
    }
}
