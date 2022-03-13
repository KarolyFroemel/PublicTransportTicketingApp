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
import ptc.springframework.publictransportrest.enums.HistoryType;
import ptc.springframework.publictransportrest.enums.TicketStatus;
import ptc.springframework.publictransportrest.exceptions.TicketException;
import ptc.springframework.publictransportrest.exceptions.TicketTypeExeption;
import ptc.springframework.publictransportrest.exceptions.UserException;
import ptc.springframework.publictransportrest.mappers.EnumMapper;
import ptc.springframework.publictransportrest.mappers.TicketMapper;
import ptc.springframework.publictransportrest.repositories.HistoryRepository;
import ptc.springframework.publictransportrest.repositories.TicketRepository;
import ptc.springframework.publictransportrest.repositories.TicketTypeRepository;
import ptc.springframework.publictransportrest.repositories.UserRepository;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    private final UserRepository userRepository;

    private final HistoryRepository historyRepository;

    public TicketService(TicketMapper ticketMapper,
                         TicketRepository ticketRepository,
                         TicketTypeRepository ticketTypeRepository,
                         UserService userService,
                         EnumMapper enumMapper,
                         UserRepository userRepository,
                         HistoryRepository historyRepository) {
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.userService = userService;
        this.enumMapper = enumMapper;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
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

        if(!user.getAccount().checkPayingCapacity(ticketType.getPrice())) {
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
        ticket = ticketRepository.save(ticket);
        user.getAccount().deductFee(ticketType.getPrice());
        UserHistory userHistory = UserHistory.builder()
                .userId(user.getId())
                .historyType(HistoryType.PURCHASE)
                .ticketId(ticket.getId())
                .balanceBefore(user.getAccount().getBalance())
                .createdOn(LocalDateTime.now())
                .build();
        user = userRepository.save(user);
        userHistory.setBalanceAfter(user.getAccount().getBalance());
        historyRepository.save(userHistory);
    }

    public void validateTicket(UUID ticketId) {
        User user = userService.getUser();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new TicketException(
                        TICKET_NOT_FOUND,
                        "Ticket not found!",
                        "Ticket not found by id in database!",
                        HttpStatus.NOT_FOUND
                )
        );

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new TicketException(
                    TICKET_IS_NOT_BELONG_TO_USER,
                    "Ticket is not belong to user!",
                    "Ticket is not belong to user!",
                    HttpStatus.BAD_REQUEST
            );
        }

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
        UserHistory userHistory = UserHistory.builder()
                .userId(user.getId())
                .ticketId(ticket.getId())
                .historyType(HistoryType.VALIDATE)
                .createdOn(LocalDateTime.now())
                .build();
        historyRepository.save(userHistory);
    }

    public void refund(UUID ticketId) {
        User user = userService.getUser();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new TicketException(
                        TICKET_NOT_FOUND,
                        "Ticket not found!",
                        "Ticket not found by id in database!",
                        HttpStatus.NOT_FOUND
                )
        );

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new TicketException(
                    TICKET_IS_NOT_BELONG_TO_USER,
                    "Ticket is not belong to user!",
                    "Ticket is not belong to user!",
                    HttpStatus.BAD_REQUEST
            );
        }

        if(ticket.getStatus() != TicketStatus.CAN_BE_USED) {
            throw new TicketException(
                    TICKET_IS_NOT_REFUNDABLE,
                    "Ticket is not refundable!",
                    "Ticket is not refundable!",
                    HttpStatus.BAD_REQUEST
            );
        }

        UserHistory userHistory = UserHistory.builder()
                .userId(user.getId())
                .historyType(HistoryType.REFUND)
                .ticketId(ticket.getId())
                .balanceBefore(user.getAccount().getBalance())
                .createdOn(LocalDateTime.now())
                .build();

        ticket.setStatus(TicketStatus.REFUNDED);
        ticketRepository.save(ticket);
        userService.fillBalance(ticket.getTicketType().getPrice());
        userHistory.setBalanceAfter(userService.getUserAccountBalance());
        historyRepository.save(userHistory);
    }

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
            if(ticket.getStatus() == TicketStatus.CAN_BE_USED && LocalDateTime.now().isAfter(ticket.getValidTo())) {
                ticket.setStatus(TicketStatus.EXPIRED);
            }
        });

        ticketRepository.saveAll(ticketList);
    }

    public TicketModel getTicket(UUID ticketId){
        User user = userService.getUser();

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new TicketException(
                        TICKET_NOT_FOUND,
                        "Ticket not found!",
                        "Ticket not found by id in database!",
                        HttpStatus.NOT_FOUND
                )
        );

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new TicketException(
                    TICKET_IS_NOT_BELONG_TO_USER,
                    "Ticket is not belong to user!",
                    "Ticket is not belong to user!",
                    HttpStatus.BAD_REQUEST
            );
        }

        return ticketMapper.ticketEntityToTicketModel(ticket);
    }
}
