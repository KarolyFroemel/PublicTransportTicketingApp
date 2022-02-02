package ptc.springframework.publictransportrest.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
//@SpringBootTest(classes = TestSecurityConfig.class)
class TicketTypeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    @DisplayName("Get ticket by id.")
    void getTicketTypeById() throws Exception {
        mvc.perform(get("/ticketType/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}