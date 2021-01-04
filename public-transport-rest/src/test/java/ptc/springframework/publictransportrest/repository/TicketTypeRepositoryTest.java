package ptc.springframework.publictransportrest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ptc.springframework.publictransportrest.model.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TicketTypeRepositoryTest {

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Test
    @DirtiesContext
    public void findAllticketTypes() {
        List<TicketType> ticketTypesTest = new ArrayList<>();

        ticketTypesTest.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Single ticket")
                .description("Single ticket for one ride. Valid to 60 minutes from validation.")
                .price(360L).build());

        ticketTypesTest.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Group of single tickets.")
                .description("This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation.")
                .price(3000L).build());

        ticketTypesTest.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Monthly pass")
                .description("Montly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase.")
                .price(10000L).build());

        ticketTypesTest.add(TicketType.builder()
                .id(UUID.randomUUID())
                .name("Daily pass")
                .description("Daily pass to use any service (Bus, Tram, Underground, Ship). Valid only purchase day.")
                .price(1500L).build());

        ticketTypesTest.forEach(ticketType -> ticketTypeRepository.save(ticketType));
        List<TicketType> ticketTypes = ticketTypeRepository.findAll();

        assertEquals(4, ticketTypes.size());
    }


    @Test
    @DirtiesContext
    public void emptyTicketTypesTable() {

        List<TicketType> ticketTypes = ticketTypeRepository.findAll();

        assertEquals(0, ticketTypes.size());
    }
}