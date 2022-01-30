package ptc.springframework.publictransportrest.controllers;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @RequestMapping({ "/helloadmin" })
    public String admin() {

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        Principal principal = (Principal) authentication.getPrincipal();

        if (principal instanceof KeycloakPrincipal) {

            KeycloakPrincipal kPrincipal = (KeycloakPrincipal) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext().getToken();

            System.out.println("User id: " + token.getSubject());
        }
        return "Hello admin";
    }


    @RequestMapping({ "/hellopassenger" })
    public String passenger() {
        return "Hello passenger";
    }

    @RequestMapping({ "/hellovalidator" })
    public String validator() {
        return "Hello validator";
    }

    @RequestMapping({ "/alluser" })
    public String allUser() {
        return "Hello all users";
    }
}
