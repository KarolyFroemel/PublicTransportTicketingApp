package ptc.springframework.publictransportrest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ptc.springframework.publictransportrest.model.User;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles(profiles = "test")
@DataJpaTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Test
    public void saveUser() {
        User savedUser = userRepository.save(
                User.builder()
                        .id(UUID.randomUUID())
                        .name("Edinson Cavani")
                        .email("edinson.cavani@gmail.com")
                        .balance(1000000L)
                        .build()
        );

        assertNotNull(savedUser);

        Optional<User> user = userRepository.findById(savedUser.getId());

        assertEquals("Edinson Cavani", user.get().getName());
        assertEquals("edinson.cavani@gmail.com", user.get().getEmail());
        assertEquals(1000000L, user.get().getBalance());
    }

}