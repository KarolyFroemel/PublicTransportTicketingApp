package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.TokenApi;
import contract.ticket.model.CredentialsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ptc.springframework.publictransportrest.services.TokenService;

import javax.validation.Valid;

@RestController
public class TokenController implements TokenApi {

    @Autowired
    private TokenService tokenService;

    @Override
    public ResponseEntity<String> getToken(@Valid @RequestBody CredentialsModel credentialsModel) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.getToken(credentialsModel));
    }
}
