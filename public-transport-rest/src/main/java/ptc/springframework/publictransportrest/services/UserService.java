package ptc.springframework.publictransportrest.services;

import contract.ticket.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.entities.User;
import ptc.springframework.publictransportrest.exceptions.UserException;
import ptc.springframework.publictransportrest.keycloak.KeycloakService;
import ptc.springframework.publictransportrest.mappers.UserMapper;
import ptc.springframework.publictransportrest.repositories.UserRepository;

import javax.transaction.Transactional;

import static ptc.springframework.publictransportrest.exceptions.error.UserErrorCode.USER_NOT_FOUND;

@Service
@Transactional
public class UserService {

    private final KeycloakService keycloakService;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserService(KeycloakService keycloakService,
                       UserMapper userMapper,
                       UserRepository userRepository) {
        this.keycloakService = keycloakService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
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
        user.setBalance(user.getBalance()+addToBalance);
        userRepository.save(user);
    }

    public Integer getUserAccountBalance(){
        User user = getUser();
        return user.getBalance();
    }

    public boolean checkPayingCapacity(int price) {
        User user = getUser();
        return user.getBalance() - price >= 0;
    }

    public void deductFee(int price) {
        User user = getUser();
        user.setBalance(user.getBalance() + price);
    }
}
