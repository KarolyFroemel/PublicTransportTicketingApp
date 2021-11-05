package ptc.springframework.publictransportrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.entity.User;
import ptc.springframework.publictransportrest.security.JwtTokenUtil;
import ptc.springframework.publictransportrest.security.JwtUserDetailsService;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    public String createToken(final String username, final String password) {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            final String token = jwtTokenUtil.generateToken(userDetails);

            return token;
    }

    public void registerNewUser(User user) {
        userDetailsService.registerNewUser(user);
    }
}
