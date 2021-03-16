package ptc.springframework.publictransportrest.testdata;

import ptc.springframework.publictransportrest.model.Account;
import ptc.springframework.publictransportrest.model.User;

import java.util.UUID;

public class AccountTestData {

    public static Account getNewTestAccount(User user) {
        return Account
                .builder()
                .id(UUID.randomUUID())
                .user(user)
                .lastModification(DateTestData.getCurrentLocalDateTime())
                .balance(1000000L)
                .build();
    }
}
