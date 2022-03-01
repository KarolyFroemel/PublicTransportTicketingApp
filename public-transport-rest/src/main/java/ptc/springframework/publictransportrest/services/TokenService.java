package ptc.springframework.publictransportrest.services;

import contract.ticket.model.CredentialsModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TokenService {

    @Value( "${keycloak.realm}" )
    private String realm;

    @Value( "${keycloak.auth-server-url}" )
    private String url;

    @Value( "${keycloak.resource}" )
    private String keycloakResource;

    @Value( "${keycloak.credentials.secret}" )
    private String keycloakSecret;

    public String getToken(CredentialsModel credentialsModel) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", keycloakResource);
        formData.add("client_secret", keycloakSecret);
        formData.add("username", credentialsModel.getUsername());
        formData.add("password", credentialsModel.getPassword());

        String response = WebClient.create()
                .post()
                .uri(url + "/realms/"+ realm + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        return response;
    }


}
