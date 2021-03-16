package ptc.springframework.publictransportrest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.repository.UserRepository;
import ptc.springframework.publictransportrest.testdata.UserTestData;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser() {
        //given
        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(UserTestData.getDavidUser()));

        //when
        User user = userService.getUser(UUID.randomUUID());

        assertNotNull(user);

        //then
        Mockito.verify(userRepository, times(1)).findById(any(UUID.class));

    }
}