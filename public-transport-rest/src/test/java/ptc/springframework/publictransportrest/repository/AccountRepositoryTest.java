package ptc.springframework.publictransportrest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ptc.springframework.publictransportrest.exception.AccountNotFoundException;
import ptc.springframework.publictransportrest.model.Account;
import ptc.springframework.publictransportrest.model.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Test
    public void saveLoadAccount() {

        User user = User.builder()
                        .id(UUID.randomUUID())
                        .name("Edinson Cavani")
                        .email("edinson.cavani@gmail.com")
                        .build();

        user.setAccount(Account.builder()
                .user(user)
                .balance(7777L)
                .id(UUID.randomUUID())
                .build());

        userRepository.save(user);

        Account loadedAccount = accountRepository.findById(user.getAccount().getId())
                .orElseThrow(AccountNotFoundException::new);


        assertEquals(user.getAccount().getBalance(), loadedAccount.getBalance());
    }
}