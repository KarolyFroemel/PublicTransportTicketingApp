package ptc.springframework.publictransportrest.services;

import contract.ticket.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.entities.*;
import ptc.springframework.publictransportrest.enums.TicketStatus;
import ptc.springframework.publictransportrest.exceptions.TicketException;
import ptc.springframework.publictransportrest.exceptions.TicketTypeExeption;
import ptc.springframework.publictransportrest.exceptions.UserException;
import ptc.springframework.publictransportrest.mappers.EnumMapper;
import ptc.springframework.publictransportrest.mappers.TicketMapper;
import ptc.springframework.publictransportrest.repositories.TicketRepository;
import ptc.springframework.publictransportrest.repositories.TicketTypeRepository;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ptc.springframework.publictransportrest.exceptions.error.TicketErrorCode.*;
import static ptc.springframework.publictransportrest.exceptions.error.TicketTypeErrorCode.TICKET_TYPE_NOT_FOUND;
import static ptc.springframework.publictransportrest.exceptions.error.UserErrorCode.USER_BALANCE_LESS_THAN_NEEDED;

@Service
@Transactional
public class TicketService {

    private final TicketMapper ticketMapper;

    private final TicketRepository ticketRepository;

    private final TicketTypeRepository ticketTypeRepository;

    private final UserService userService;

    private final EnumMapper enumMapper;

    public TicketService(TicketMapper ticketMapper,
                         TicketRepository ticketRepository,
                         TicketTypeRepository ticketTypeRepository,
                         UserService userService,
                         EnumMapper enumMapper) {
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.userService = userService;
        this.enumMapper = enumMapper;
    }

    public void purchaseTicket(PurchaseTicketModel purchaseTicketModel) {
        if(purchaseTicketModel.getValidFrom().isBefore(LocalDate.now())) {
            throw new TicketException(
                    TICKET_VALID_FROM_IS_IN_THE_PAST,
                    "Incorrect date.",
                    "Ticket validity date is in the past.",
                    HttpStatus.BAD_REQUEST
            );
        }

        User user = userService.getUser();
        TicketType ticketType = ticketTypeRepository.findById(purchaseTicketModel.getTicketId()).orElseThrow(
                () -> new TicketTypeExeption(TICKET_TYPE_NOT_FOUND,
                        "Ticket type not found!",
                        "Ticket type not found by id in database!",
                        HttpStatus.NOT_FOUND)
        );

        if(!userService.checkPayingCapacity(ticketType.getPrice())) {
            throw new UserException(
                    USER_BALANCE_LESS_THAN_NEEDED,
                    "User has less balance than needed.",
                    "User has less money than the selected ticket price needed.",
                    HttpStatus.FORBIDDEN
            );
        }

        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setPurchaseDate(LocalDateTime.now());
        ticket.setStatus(TicketStatus.CAN_BE_USED);
        ticket.setUser(userService.getUser());
        ticket.setValidFrom(purchaseTicketModel.getValidFrom().atStartOfDay());
        ticket.setValidTo(purchaseTicketModel.getValidFrom().atStartOfDay().plusDays(ticketType.getExpirationTime()));
        ticketRepository.save(ticket);
        userService.deductFee(ticketType.getPrice());
    }

    public void validateTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new TicketException(
                        TICKET_NOT_FOUND,
                        "Ticket not found!",
                        "Ticket not found by id in database!",
                        HttpStatus.NOT_FOUND
                )
        );

        if (!ticket.getTicketType().getIsEnforceable()) {
            throw new TicketException(
                    TICKET_IS_NOT_ENFORCEABLE,
                    "Ticket is not enforceable!",
                    "Ticket is not enforceable!",
                    HttpStatus.BAD_REQUEST
            );
        }

        ticket.setValidationTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.VALIDATED);
        ticketRepository.save(ticket);
    }

    public void refund(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new TicketException(
                        TICKET_NOT_FOUND,
                        "Ticket not found!",
                        "Ticket not found by id in database!",
                        HttpStatus.NOT_FOUND
                )
        );

        if(ticket.getStatus() != TicketStatus.CAN_BE_USED) {
            throw new TicketException(
                    TICKET_IS_NOT_REFUNDABLE,
                    "Ticket is not refundable!",
                    "Ticket is not refundable!",
                    HttpStatus.BAD_REQUEST
            );
        }

        ticket.setStatus(TicketStatus.REFUNDED);
        ticketRepository.save(ticket);
        userService.fillBalance(ticket.getTicketType().getPrice());
    }

//    public List<TicketModel> getAllTicket() {
//        User user = userService.getUser();
//        List<Ticket> ticketList = user.getTicketList();
//        List<TicketModel> ticketModelList = new ArrayList<>();
//        System.out.println(ticketList.get(0).getStatus()==TicketStatus.CAN_BE_USED);
//        ticketList.forEach(ticket -> {
//            System.out.println(ticket.getStatus()==TicketStatus.CAN_BE_USED);
//        });
////        for (Ticket ticket: ticketList) {
////            if(ticket.getStatus().equals(TicketStatus.CAN_BE_USED)){
////                ticketModelList.add(ticketMapper.ticketEntityToTicketModel(ticket));
////            }
////        }
//
//        return ticketMapper.ticketEntityListToTicketModelList(ticketList);
//    }

    public Page<TicketModel> searchTicket(final int pageNumber,
                                              final int pageSize,
                                              final TicketSearchModel ticketSearchModel) {

        Pageable pageable;

        if("ASC".equals(ticketSearchModel.getSortOrder().getValue())) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(ticketSearchModel.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(ticketSearchModel.getSortBy()).descending());
        }


        Page<Ticket> ticketPage = ticketRepository.findAll(
                createSearchSpecification(ticketSearchModel),
                pageable
        );

        return ticketPage.map(this.ticketMapper::ticketEntityToTicketModel);
    }

    private Specification<Ticket> createSearchSpecification(TicketSearchModel ticketSearchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            User user = userService.getUser();

            predicates.add(criteriaBuilder.equal(
                    root.get(Ticket_.user).get(User_.id), user.getId()));


            if (ticketSearchModel.getTicketType() != null && !ticketSearchModel.getTicketType().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        root.get(Ticket_.ticketType).get(TicketType_.name), ticketSearchModel.getTicketType()));
            }

            if (ticketSearchModel.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get(Ticket_.status), enumMapper.ticketSearchModelTicketStatusToTicketEntityTicketStatus(ticketSearchModel.getStatus())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public void updateTicketStatus(){
        User user = userService.getUser();
        List<Ticket> ticketList = user.getTicketList();
        ticketList.forEach(ticket -> {
            if(ticket.getStatus() == TicketStatus.CAN_BE_USED && ticket.getValidTo().isAfter(LocalDateTime.now())) {
                ticket.setStatus(TicketStatus.EXPIRED);
            }
        });

        ticketRepository.saveAll(ticketList);
    }

//    public List<TicketModel> getTicketsLambda(TicketSearchModel ticketSearchModel) {
//        List<Ticket> ticketList = userService.getUser().getTicketList();
//
//        if(ticketSearchModel.getTicketType() != null && !ticketSearchModel.getTicketType().isEmpty()) {
//            ticketList = ticketList
//                    .stream()
//                    .filter(ticket -> ticket.getTicketType().getName().equals(ticketSearchModel.getTicketType()))
//                    .collect(Collectors.toList());
//        }
//
//        if(ticketSearchModel.getStatus() != null) {
//
//            ticketList = ticketList
//                    .stream()
//                    .filter(ticket -> ticket.getStatus().equals(ticketSearchModel.getStatus()))
//                    .collect(Collectors.toList());
//        }
//
//
//        if (ticketSearchModel.getSortBy().equals("ticketType")) {
//
//            if (ticketSearchModel.getSortOrder().getValue().equals("ASC")) {
//                ticketList = ticketList
//                        .stream()
//                        .sorted(Comparator.comparing(TicketType::getName))
//                        .collect(Collectors.toList());
//            } else {
//                ticketTypeList = ticketTypeList
//                        .stream()
//                        .sorted(Comparator.comparing(TicketType::getName).reversed())
//                        .collect(Collectors.toList());
//            }
//
//        } else {
//
//            if (ticketTypeSearchRequestModel.getSortOrder().getValue().equals("ASC")) {
//                ticketTypeList = ticketTypeList
//                        .stream()
//                        .sorted(Comparator.comparing(TicketType::getDescription))
//                        .collect(Collectors.toList());
//            } else {
//                ticketTypeList = ticketTypeList
//                        .stream()
//                        .sorted(Comparator.comparing(TicketType::getDescription).reversed())
//                        .collect(Collectors.toList());
//            }
//        }
//
//
//        return ticketTypeMapper.ticketTypeEntityListToTicketTypeModelList(ticketTypeList);
//    }
}
