package ptc.springframework.publictransportrest.testdata;

import org.springframework.stereotype.Component;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TicketTestData {

    public static Ticket getSingleTicket(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getSingleTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .validFrom(DateTestData.getCurrentLocalDateTime())
                .validTo(DateTestData.getPlusOneYearLocalDateTime())
                .build();
    }

    public static Ticket getValidatedSingleTicket(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getSingleTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .validFrom(DateTestData.getCurrentLocalDateTime())
                .validTo(DateTestData.getPlusOneYearLocalDateTime())
                .validationDate(DateTestData.getYesterdayLocalDateTime())
                .build();
    }

    public static Ticket getMonthlyPass(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getMontlyPassTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .validFrom(DateTestData.getCurrentLocalDateTime())
                .validTo(DateTestData.getPlusOneMonthLocalDateTime())
                .build();
    }

    public static Ticket getExpiredMonthlyPass(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getMontlyPassTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .validFrom(DateTestData.getCurrentLocalDateTime())
                .validTo(DateTestData.getYesterdayLocalDateTime())
                .build();
    }

    public static Ticket getDailyPass(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getDailyPassTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .validFrom(DateTestData.getCurrentLocalDateTime())
                .validTo(DateTestData.getTomorrowLocalDateTime())
                .build();
    }

    public static Ticket getExpiredDailyPass(User user) {

        return Ticket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .ticketType(TicketTypeTestData.getDailyPassTicketType())
                .purchaseDate(DateTestData.getCurrentLocalDateTime())
                .validFrom(DateTestData.getCurrentLocalDateTime())
                .validTo(DateTestData.getYesterdayLocalDateTime())
                .build();
    }

    public static List<Ticket> getTicketList(User user) {
        List<Ticket> ticketTicketList = new ArrayList<>();

        ticketTicketList.add(getSingleTicket(user));
        ticketTicketList.add(getMonthlyPass(user));
        ticketTicketList.add(getDailyPass(user));

        return ticketTicketList;
    }
}
