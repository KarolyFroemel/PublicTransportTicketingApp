package ptc.springframework.publictransportrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import contract.ticket.model.FillBalanceModel;
import contract.ticket.model.UserModel;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ptc.springframework.publictransportrest.configurations.TestSecurityConfig;
import ptc.springframework.publictransportrest.keycloak.KeycloakService;
import ptc.springframework.publictransportrest.testdata.UserTestData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class)
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class UserControllerTest {

    private final UUID USER_ID = UUID.fromString("1958fb4a-2f76-480c-8df2-6045f3c017a5");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private KeycloakService keycloakService;

    private final ObjectMapper mapper = new ObjectMapper();

    private UserTestData userTestData = new UserTestData();

    @BeforeEach
    public void init() {
        Mockito.lenient().when(keycloakService.getUserId()).thenReturn(USER_ID);
    }

    @Test
    @DisplayName("Get user balance.")
    @Order(1)
    void getUserAccountBalance() throws Exception {
        mvc.perform(get("/user/getBalance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(userTestData.getUserInfoResult().getAccount().getBalance()));
    }

    @Test
    @DisplayName("Get user info.")
    @Order(2)
    void getUserInfo() throws Exception {
        UserModel userModel = userTestData.getUserInfoResult();
        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userModel.getId().toString()))
                .andExpect(jsonPath("$.name").value(userModel.getName()))
                .andExpect(jsonPath("$.email").value(userModel.getEmail()))
                .andExpect(jsonPath("$.account.balance").value(userModel.getAccount().getBalance().toString()));
    }

    @Test
    @DisplayName("Get non existing user balance.")
    @Order(3)
    void getNonExistingUserInfo() throws Exception {
        Mockito.lenient().when(keycloakService.getUserId()).thenReturn(UUID.randomUUID());
        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("USER001"))
                .andExpect(jsonPath("$.message").value("User not found."))
                .andExpect(jsonPath("$.detailedMessage").value("User not found in database."));
    }

    @Test
    @DisplayName("Fill user balance.")
    @Order(4)
    void fillUserAccountBalance() throws Exception {
        FillBalanceModel fillBalanceModel = userTestData.getFillBalanceModel();
        mvc.perform(get("/user/getBalance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(userTestData.getUserInfoResult().getAccount().getBalance()));

        mvc.perform(put("/user/fillBalance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userTestData.getFillBalanceModel())))
                .andExpect(status().isNoContent());


        mvc.perform(get("/user/getBalance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(userTestData.
                        getUserInfoResult().
                        getAccount().
                        getBalance() +
                        userTestData.
                                getFillBalanceModel().
                                getAddBalance()));

    }

    @Test
    void searchInAccountHistory() {
    }
}