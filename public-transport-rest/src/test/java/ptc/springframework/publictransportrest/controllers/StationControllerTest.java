package ptc.springframework.publictransportrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import contract.ticket.model.StationModel;
import contract.ticket.model.StationSearchModel;
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
import ptc.springframework.publictransportrest.testdata.StationTestData;

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
class StationControllerTest {

    private final UUID USER_ID = UUID.fromString("eb5e3e64-1153-410d-bfc7-9988766c82ac");
    private final UUID STATION_ID = UUID.fromString("a1fcdcb2-28ef-4458-bca1-5c057ac6e3e2");

    private final ObjectMapper mapper = new ObjectMapper();

    private StationTestData stationTestData = new StationTestData();

    @MockBean
    private KeycloakService keycloakService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void init() {
        Mockito.lenient().when(keycloakService.getUserId()).thenReturn(USER_ID);
    }

    @Test
    @DisplayName("Search station ASC.")
    @Order(1)
    void searchStationASC() throws Exception {
        StationSearchModel stationSearchModel = stationTestData.getStationSearchModelASC();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/station/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(stationSearchModel)))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "1"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "1"));
    }

    @Test
    @DisplayName("Search station DESC.")
    @Order(2)
    void searchStationDESC() throws Exception {
        StationSearchModel stationSearchModel = stationTestData.getStationSearchModelDESC();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/station/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(stationSearchModel)))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "1"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "1"));
    }

    @Test
    @DisplayName("Search station empty.")
    @Order(3)
    void searchStationEmpty() throws Exception {
        StationSearchModel stationSearchModel = stationTestData.getStationSearchModelEmpty();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Page", "0");
        requestHeaders.set("X-Size", "3");

        mvc.perform(post("/station/search")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(requestHeaders)
                .content(mapper.writeValueAsString(stationSearchModel)))
                .andExpect(status().isPartialContent())
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Page", "0"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Size", "3"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Pages", "12"))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .stringValues("X-Total-Size", "34"));
    }

    @Test
    @DisplayName("Create new station.")
    @Order(4)
    void createNewStation() throws Exception {
        mvc.perform(post("/station")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(stationTestData.getCreateStationModel())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(stationTestData.getCreateStationModel().getName()));
    }

    @Test
    @DisplayName("Get station.")
    @Order(5)
    void getStationById() throws Exception {
        StationModel stationModel = stationTestData.getResult();
        mvc.perform(get("/station/{id}", stationModel.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stationModel.getId().toString()))
                .andExpect(jsonPath("$.name").value(stationModel.getName()));
    }

    @Test
    @DisplayName("Get non existing station.")
    @Order(6)
    void getNonExistingStationById() throws Exception {
        StationModel stationModel = stationTestData.getResult();
        mvc.perform(get("/station/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("STATION001"))
                .andExpect(jsonPath("$.message").value("Station not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Station not found in database"));
    }

    @Test
    @DisplayName("Update station.")
    @Order(7)
    void updateStation() throws Exception {
        mvc.perform(put("/station", STATION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(stationTestData.getStationModel())))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete station.")
    @Order(8)
    void deleteStationById() throws Exception {
        mvc.perform(delete("/station/{id}", STATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete non existing station.")
    @Order(9)
    void deleteNonExistingStationById() throws Exception {
        mvc.perform(delete("/station/{id}", STATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("STATION001"))
                .andExpect(jsonPath("$.message").value("Station not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Station not found in database"));
    }

    @Test
    @DisplayName("Update non existing station.")
    @Order(10)
    void updateNonExistingStation() throws Exception {
        mvc.perform(put("/station")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(stationTestData.getStationModelNonExisting())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("STATION001"))
                .andExpect(jsonPath("$.message").value("Station not found."))
                .andExpect(jsonPath("$.detailedMessage").value("Station not found in database"));
    }
}