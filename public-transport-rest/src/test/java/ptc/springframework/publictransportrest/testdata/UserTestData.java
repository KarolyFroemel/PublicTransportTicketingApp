package ptc.springframework.publictransportrest.testdata;

import ptc.springframework.publictransportrest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserTestData {

    public static User getEdisonUser() {
        User user = User.builder()
                .id(UUID.fromString("472ea919-f2e6-48e8-b373-e7268b3b2dd8"))
                .name("Edinson Cavani")
                .email("edinson.cavani@gmail.com")
                .build();

        user.setAccount(AccountTestData.getNewTestAccount(user));

        user.setTickets(TicketTestData.getTicketList(user));

        return user;
    }

    public static User getDavidUser() {
        User user =  User.builder()
                .id(UUID.fromString("f1a0e02d-2134-4e95-84b7-e729534d49e2"))
                .name("David Beckham")
                .email("david.beckham@gmail.com")
                .build();

        user.setAccount(AccountTestData.getNewTestAccount(user));

        user.setTickets(TicketTestData.getTicketList(user));

        return user;
    }

    public static List<User> getUserList () {
        List<User> userList = new ArrayList<>();

        userList.add(getEdisonUser());
        userList.add(getDavidUser());

        return userList;
    }
 }
