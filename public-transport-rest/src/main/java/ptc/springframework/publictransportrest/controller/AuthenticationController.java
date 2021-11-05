package ptc.springframework.publictransportrest.controller;

import contract.ticket.api.AuthenticateApi;
import contract.ticket.model.AuthenticateModel;
import contract.ticket.model.TokenModel;
import contract.ticket.model.UserRegisterModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ptc.springframework.publictransportrest.mapper.UserRegisterMapper;
import ptc.springframework.publictransportrest.service.AuthenticationService;

import javax.validation.Valid;

@RestController
public class AuthenticationController implements AuthenticateApi {

    private final AuthenticationService authenticationService;

    private final UserRegisterMapper userRegisterMapper;

    public AuthenticationController(AuthenticationService authenticationService, UserRegisterMapper userRegisterMapper) {
        this.authenticationService = authenticationService;
        this.userRegisterMapper = userRegisterMapper;
    }

    @Override
    public ResponseEntity<TokenModel> createAuthenticationToken(@Valid @RequestBody AuthenticateModel authenticateModel) {
        TokenModel token = new TokenModel();
        token.setToken(authenticationService.createToken(authenticateModel.getUsername(), authenticateModel.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @Override
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterModel userRegisterModel) {
        authenticationService.registerNewUser(userRegisterMapper.UserRegisterModelToUser(userRegisterModel));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
