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
                .price(360L)
                .expirationTime(366L)
                .build();
    }

    public static TicketType getMontlyPassTicketType() {
        return TicketType.builder()
                .id(UUID.randomUUID())
                .name("Monthly pass")
                .description("Monthly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase.")
                .price(10000L)
                .expirationTime(31L)
                .build();
    }

    public static TicketType getDailyPassTicketType() {
        return TicketType.builder()
                .id(UUID.randomUUID())
                .name("Daily pass")
                .description("Daily pass to use any service (Bus, Tram, Underground, Ship). Valid only purchase day.")
                .price(1500L)
                .expirationTime(1L)
                .build();
    }

    public static List<TicketType> getTicketTypeList() {
        List<TicketType> ticketTypeList = new ArrayList<>();
        ticketTypeList.add(getSingleTicketType());
        ticketTypeList.add(getMontlyPassTicketType());
        ticketTypeList.add(getDailyPassTicketType());
        return ticketTypeList;
    }
}
