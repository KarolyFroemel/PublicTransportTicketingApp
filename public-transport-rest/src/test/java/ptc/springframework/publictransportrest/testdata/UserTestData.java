package ptc.springframework.publictransportrest.testdata;

import ptc.springframework.publictransportrest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserTestData {

    public static User getEdisonUser() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Edinson Cavani")
                .email("edinson.cavani@gmail.com")
                .balance(1000000L)
                .build();

        user.setTickets(TicketTestData.getTicketList(user));

        return user;
    }

    public static User getDavidUser() {
        User user =  User.builder()
                .id(UUID.randomUUID())
                .name("David Beckham")
                .email("david.beckham@gmail.com")
                .balance(1000000L)
                .build();

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
