package ptc.springframework.publictransportrest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.enums.Role;
import ptc.springframework.publictransportrest.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private String ROLE_PREFIX = "ROLE_";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;

        ptc.springframework.publictransportrest.entity.User user = userRepository.findByEmail(email);
        if (user != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().toString()));
            return new User(user.getEmail(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with the name " + email);
    }

    public void registerNewUser(ptc.springframework.publictransportrest.entity.User user) {
        user.setId(UUID.randomUUID());
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        user.setRole(Role.PASSENGER);
        user.setNumberOfIncorrectLogins(0);
        user.setLocked(false);
        userRepository.save(user);
    }
}
