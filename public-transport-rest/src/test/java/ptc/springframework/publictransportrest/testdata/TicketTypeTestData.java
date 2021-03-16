package ptc.springframework.publictransportrest.testdata;

import ptc.springframework.publictransportrest.model.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketTypeTestData {

    private static final UUID sindelTicketId = UUID.fromString("43fe7a42-6983-462d-8701-d7f23bc9a5d2");
    private static final UUID monthlyPassId = UUID.fromString("f2427b4d-e6d5-4554-a0be-3eddf5d069f8");
    private static final UUID dailyPassId = UUID.fromString("6dcfa779-37d5-4b29-9c73-4636552940c5");

    public static TicketType getSingleTicketType() {
        return TicketType.builder()
                .id(sindelTicketId)
                .name("Single ticket")
                .description("Single ticket for one ride. Valid to 60 minutes from validation.")
                .price(1L)
                .expirationTime(366L)
                .build();
    }

    public static TicketType getMontlyPassTicketType() {
        return TicketType.builder()
                .id(monthlyPassId)
                .name("Monthly pass")
                .description("Monthly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase.")
                .price(30L)
                .expirationTime(31L)
                .build();
    }

    public static TicketType getDailyPassTicketType() {
        return TicketType.builder()
                .id(dailyPassId)
                .name("Daily pass")
                .description("Daily pass to use any service (Bus, Tram, Underground, Ship). Valid only purchase day.")
                .price(10L)
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
