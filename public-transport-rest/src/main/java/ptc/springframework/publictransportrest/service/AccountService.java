package ptc.springframework.publictransportrest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.model.Account;
import ptc.springframework.publictransportrest.repository.AccountRepository;

import java.util.UUID;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    public Long getUserBalance(UUID userId) {
        log.info("Getting user balance with userid: {}", userId);
        return userService.getUser(userId).getAccount().getBalance();
    }

    public void addToBalance(UUID userId, Long plusAmount) {
        log.info("Filling user balance with userid: {}, balance: {}.", userId, plusAmount);
        Account account = userService.getUser(userId).getAccount();
        account.fillBalance(plusAmount);
        accountRepository.save(account);
    }
}
