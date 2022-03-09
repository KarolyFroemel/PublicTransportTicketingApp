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
import ptc.springframework.publictransportrest.exceptions.UserException;
import ptc.springframework.publictransportrest.keycloak.KeycloakService;
import ptc.springframework.publictransportrest.mappers.EnumMapper;
import ptc.springframework.publictransportrest.mappers.UserMapper;
import ptc.springframework.publictransportrest.repositories.HistoryRepository;
import ptc.springframework.publictransportrest.repositories.UserRepository;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ptc.springframework.publictransportrest.exceptions.error.UserErrorCode.USER_NOT_FOUND;

@Service
@Transactional
public class UserService {

    private final KeycloakService keycloakService;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final HistoryRepository historyRepository;

    private final EnumMapper enumMapper;

    public UserService(KeycloakService keycloakService,
                       UserMapper userMapper,
                       UserRepository userRepository,
                       HistoryRepository historyRepository,
                       EnumMapper enumMapper) {
        this.keycloakService = keycloakService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.enumMapper = enumMapper;
    }

    public User getUser() {
        return userRepository.findById(keycloakService.getUserId()).orElseThrow(
                () -> new UserException(
                        USER_NOT_FOUND,
                        "User not found.",
                        "User not found in database.",
                        HttpStatus.NOT_FOUND
                )
        );
    }

    public UserModel getUserInfo() {
        User user = getUser();
        return userMapper.userEntityToUserModel(user);
    }

    public void fillBalance(int addToBalance) {
        User user = getUser();

        UserHistory userHistory = UserHistory.builder()
                        .historyType(HistoryType.FILL_BALANCE)
                        .userId(user.getId())
                        .balanceBefore(user.getAccount().getBalance())
                        .createdOn(LocalDateTime.now()).build();

        user.getAccount().addBalance(addToBalance);
        user = userRepository.save(user);
        userHistory.setBalanceAfter(user.getAccount().getBalance());
        historyRepository.save(userHistory);
    }

    public Integer getUserAccountBalance(){
        User user = getUser();
        return user.getAccount().getBalance();
    }

    public Page<UserHistoryModel> searchHistory(final int pageNumber,
                                               final int pageSize,
                                               final UserHistorySearchModel userHistorySearchModel) {

        Pageable pageable;

        if("ASC".equals(userHistorySearchModel.getSortOrder().getValue())) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(userHistorySearchModel.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(userHistorySearchModel.getSortBy()).descending());
        }


        Page<UserHistory> userHistoryPage = historyRepository.findAll(
                createSearchSpecification(userHistorySearchModel),
                pageable
        );

        return userHistoryPage.map(this.userMapper::userHistoryEntityToUserHistoryModel);
    }

    private Specification<UserHistory> createSearchSpecification(UserHistorySearchModel userHistorySearchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            User user = getUser();

            predicates.add(criteriaBuilder.equal(
                    root.get(UserHistory_.userId), user.getId()));

            if (userHistorySearchModel.getHistoryType() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get(UserHistory_.historyType), enumMapper.userHistorySearchModelHistoryTypeToHistoryType(userHistorySearchModel.getHistoryType())));
            }

            if (userHistorySearchModel.getStartDate() != null && userHistorySearchModel.getEndDate() != null) {
                predicates.add(criteriaBuilder.between(
                        root.get(UserHistory_.createdOn), userHistorySearchModel.getStartDate().atStartOfDay(), userHistorySearchModel.getEndDate().atTime(LocalTime.MAX)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
