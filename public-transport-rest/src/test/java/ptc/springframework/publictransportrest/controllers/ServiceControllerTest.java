package ptc.springframework.publictransportrest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import contract.ticket.model.CreateServiceModel;
import contract.ticket.model.ServiceSearchModel;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ptc.springframework.publictransportrest.configurations.TestSecurityConfig;
import ptc.springframework.publictransportrest.keycloak.KeycloakService;
import ptc.springframework.publictransportrest.testdata.ServiceTestData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestSecurityConfig.class)
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ServiceControllerTest {

    private final UUID USER_ID = UUID.fromString("eb5e3e64-1153-410d-bfc7-9988766c82ac");
    private final UUID SERVICE_ID = UUID.fromString("6e722e88-1703-43ff-90e7-55ff65587e29");
    private final UUID STATION_ID = UUID.fromString("3767afb0-0f85-4d35-a31e-09f65c596500");
    private final UUID CONNECTED_STATION_ID = UUID.fromString("f392a8f3-bd9f-43fe-9046-c523f47e84cd");


    @Autowired
    private MockMvc mvc;

    @MockBean
    private KeycloakService keycloakService;

    private final ObjectMapper mapper = new ObjectMapper();

    private ServiceTestData serviceTestData = new ServiceTestData();

    @BeforeEach
    public void init() {
        Mockito.lenient().when(keycloakService.getUserId()).thenReturn(USER_ID);
    }

    @Test
    @DisplayName("Search services ASC.")
    @Order(1)
    void searchServiceASC() throws Exception {
        ServiceSearchModel serviceSearchModel = serviceTestData.getSearchServiceModelASC();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/service/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(serviceSearchModel)))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "10"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "30"));
    }


    @Test
    @DisplayName("Search services DESC.")
    @Order(2)
    void searchServiceDESC() throws Exception {
        ServiceSearchModel serviceSearchModel = serviceTestData.getSearchServiceModelDESC();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/service/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(serviceSearchModel)))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "10"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "30"));
    }

    @Test
    @DisplayName("Search services empty.")
    @Order(3)
    void searchServiceEmpty() throws Exception {
        ServiceSearchModel serviceSearchModel = serviceTestData.getSearchServiceModelEmpty();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/service/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(serviceSearchModel)))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "11"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "32"));
    }

    @Test
    @DisplayName("Create new service.")
    @Order(4)
    void createNewService() throws Exception {
        CreateServiceModel createServiceModel = serviceTestData.getCreateServiceModel();
        mvc.perform(post("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createServiceModel)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Add station to service.")
    @Order(5)
    void addStationToService() throws Exception {
        mvc.perform(put("/service/" + SERVICE_ID + "/station/" + STATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Add non existing station to service.")
    @Order(6)
    void addNonExistingStationToService() throws Exception {
        mvc.perform(put("/service/" + SERVICE_ID + "/station/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("STATION001"))
                .andExpect(jsonPath("$.message").value("Station not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Station not found in database"));

    }

    @Test
    @DisplayName("Add station to non existing service.")
    @Order(7)
    void addStationToNonExistingService() throws Exception {
        mvc.perform(put("/service/" + UUID.randomUUID() + "/station/" + STATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("SERVICE001"))
                .andExpect(jsonPath("$.message").value("Service not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Service not found in database."));

    }

    @Test
    @DisplayName("Remove station from service")
    @Order(8)
    void removeStationFromService() throws Exception {
        mvc.perform(delete("/service/" + SERVICE_ID + "/station/" + CONNECTED_STATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Remove station from non existing service")
    @Order(9)
    void removeStationFromNonExistingService() throws Exception {
        mvc.perform(delete("/service/" + UUID.randomUUID() + "/station/" + STATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("SERVICE001"))
                .andExpect(jsonPath("$.message").value("Service not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Service not found in database."));
    }

    @Test
    @DisplayName("Update service.")
    @Order(10)
    void updateService() throws Exception {
        mvc.perform(put("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(serviceTestData.getServiceModelWithId(SERVICE_ID))))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Update non existing service.")
    @Order(11)
    void updateNonExistingService() throws Exception {
        mvc.perform(put("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(serviceTestData.getServiceModelWithId(UUID.randomUUID()))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("SERVICE001"))
                .andExpect(jsonPath("$.message").value("Service not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Service not found in database."));
    }

    @Test
    @DisplayName("Get service.")
    @Order(12)
    void getServiceById() throws Exception {
        mvc.perform(get("/service/" + SERVICE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Delete service by id.")
    @Order(13)
    void deleteServiceById() throws Exception {
        mvc.perform(delete("/service/" + SERVICE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Get non existing service.")
    @Order(14)
    void getNonExistingServiceById() throws Exception {
        mvc.perform(get("/service/" + SERVICE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("SERVICE001"))
                .andExpect(jsonPath("$.message").value("Service not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Service not found in database."));
    }

    @Test
    @DisplayName("Delete non existing service.")
    @Order(15)
    void deleteNonExistingServiceById() throws Exception {
        mvc.perform(delete("/service/" + SERVICE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("SERVICE001"))
                .andExpect(jsonPath("$.message").value("Service not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Service not found in database."));
    }

}