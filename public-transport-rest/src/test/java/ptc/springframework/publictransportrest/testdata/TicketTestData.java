package ptc.springframework.publictransportrest.testdata;

import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketTestData {

    public static Ticket getSingleTicket(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getSingleTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .canBeUsed(DateTestData.getPlusOneYearLocalDateTime())
                .build();
    }

    public static Ticket getGroupTickets(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getGroupTicketsType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .canBeUsed(DateTestData.getPlusOneYearLocalDateTime())
                .build();
    }

    public static Ticket getMonthlyPass(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getMontlyPassTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .canBeUsed(DateTestData.getPlusOneYearLocalDateTime())
                .build();
    }

    public static Ticket getDailyPass(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getDailyPassTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .canBeUsed(DateTestData.getPlusOneYearLocalDateTime())
                .build();
    }

    public static List<Ticket> getTicketList(User user) {
        List<Ticket> ticketTicketList = new ArrayList<>();

        ticketTicketList.add(getSingleTicket(user));
        ticketTicketList.add(getGroupTickets(user));
        ticketTicketList.add(getMonthlyPass(user));
        ticketTicketList.add(getDailyPass(user));

        return ticketTicketList;
    }
}
