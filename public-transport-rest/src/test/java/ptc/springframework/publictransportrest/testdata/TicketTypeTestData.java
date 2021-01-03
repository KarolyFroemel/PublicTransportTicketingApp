package ptc.springframework.publictransportrest.testdata;

import ptc.springframework.publictransportrest.model.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketTypeTestData {

    public static TicketType getSingleTicketType() {
        return TicketType.builder()
                .id(UUID.randomUUID())
                .name("Single ticket")
                .description("Single ticket for one ride. Valid to 60 minutes from validation.")
                .price(360L).build();
    }

    public static TicketType getGroupTicketsType() {
        return TicketType.builder()
                .id(UUID.randomUUID())
                .name("Group of single tickets.")
                .description("This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation.")
                .price(3000L).build();
    }

    public static TicketType getMontlyPassTicketType() {
        return TicketType.builder()
                .id(UUID.randomUUID())
                .name("Monthly pass")
                .description("Montly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase.")
                .price(10000L).build();
    }

    public static TicketType getDailyPassTicketType() {
        return TicketType.builder()
                .id(UUID.randomUUID())
                .name("Daily pass")
                .description("Daily pass to use any service (Bus, Tram, Underground, Ship). Valid only purchase day.")
                .price(1500L).build();
    }

    public static List<TicketType> getTicketTypeList() {
        List<TicketType> ticketTypeList = new ArrayList<>();
        ticketTypeList.add(getSingleTicketType());
        ticketTypeList.add(getGroupTicketsType());
        ticketTypeList.add(getMontlyPassTicketType());
        ticketTypeList.add(getDailyPassTicketType());
        return ticketTypeList;
    }
}
