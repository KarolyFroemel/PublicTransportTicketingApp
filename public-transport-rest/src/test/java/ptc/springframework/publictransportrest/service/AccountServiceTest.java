package ptc.springframework.publictransportrest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.repository.AccountRepository;
import ptc.springframework.publictransportrest.testdata.UserTestData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserService userService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserBalance() {
        //given
        User user = UserTestData.getEdisonUser();

        Mockito.when(userService.getUser(any(UUID.class)))
                .thenReturn(user);

        //when
        Long balance = accountService.getUserBalance(UUID.randomUUID());

        //then
        assertEquals(user.getAccount().getBalance(), balance);
        Mockito.verify(userService, times(1)).getUser(any(UUID.class));
    }

    @Test
    void fillBalance() {

        //given
        User user = UserTestData.getEdisonUser();

        Mockito.when(userService.getUser(any(UUID.class)))
                .thenReturn(user);

        //when
        accountService.addToBalance(UUID.randomUUID(), 1000L);

        //then
        Mockito.verify(userService, times(1)).getUser(any(UUID.class));
        Mockito.verify(accountRepository, times(1)).save(any());
    }
}