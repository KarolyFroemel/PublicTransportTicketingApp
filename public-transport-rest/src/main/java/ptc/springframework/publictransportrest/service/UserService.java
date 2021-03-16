package ptc.springframework.publictransportrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.exception.UserNotfoundException;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotfoundException::new);
    }
}
