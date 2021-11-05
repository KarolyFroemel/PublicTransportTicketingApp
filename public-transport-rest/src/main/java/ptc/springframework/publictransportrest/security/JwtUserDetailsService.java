package ptc.springframework.publictransportrest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.enums.Roles;
import ptc.springframework.publictransportrest.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("javainuse".equals(username)) {
            return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public void registerNewUser(ptc.springframework.publictransportrest.entity.User user) {
        user.setId(UUID.randomUUID());
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        user.setRole(Roles.PASSENGER);
        user.setNumberOfIncorrectLogins(0);
        user.setLocked(false);
        user.setCreatedBy(null);
        user.setCreatedOn(LocalDateTime.now());
        userRepository.save(user);
    }
}
