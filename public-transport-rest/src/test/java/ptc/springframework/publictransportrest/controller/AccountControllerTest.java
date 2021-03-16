package ptc.springframework.publictransportrest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ptc.springframework.publictransportrest.PublicTransportRestApplication;
import ptc.springframework.publictransportrest.exception.GlobalExceptionHandler;
import ptc.springframework.publictransportrest.mapper.AccountMapper;
import ptc.springframework.publictransportrest.service.AccountService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PublicTransportRestApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapperMock;

    @Autowired
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void fillUserBalance() throws Exception {

        //given
        Mockito.doNothing().when(accountService)
                .addToBalance(any(UUID.class), any(Long.class));

        //when
        mockMvc.perform(
                put("/account/5c27ed6d-3e3c-469d-a56e-feac99a43690/fillBalance/700")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBalance() throws Exception {

        //given
        Mockito.when(accountMapperMock.toBalanceModel(any())).thenReturn(accountMapper.toBalanceModel(790L));

        //when
        mockMvc.perform(get("/account/getBalance/5c27ed6d-3e3c-469d-a56e-feac99a43690").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(Long.valueOf(790L).toString()));
    }
}