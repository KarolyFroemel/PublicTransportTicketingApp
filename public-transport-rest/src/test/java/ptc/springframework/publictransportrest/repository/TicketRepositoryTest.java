package ptc.springframework.publictransportrest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ptc.springframework.publictransportrest.model.TicketType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@ExtendWith(SpringExtension.class)
//@TestPropertySource(locations = "classpath:application-test.properties")


//@EnableAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class, TicketRepository.class})
//@PropertySource("application-test.properties")
@ActiveProfiles(profiles = "test")
@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepository;

    @Test
    public void findAlltickets() throws Exception {
                List<TicketType> ticketTypesTest = new ArrayList<>();

        ticketTypesTest.add(new TicketType(1L,"Single ticket","Single ticket for one ride. Valid to 60 minutes from validation.",360L));
        ticketTypesTest.add(new TicketType(2L,"Group of single tickets.", "This group contains 10 single tickets. Each of single ticket for one ride. Valid to 60 minutes from validation.", 3000L));
        ticketTypesTest.add(new TicketType(3L,"Monthly pass","Montly pass to use any service (Bus, Tram, Underground, Ship). Valid to 30 days from purchase.", 10000L));
        ticketTypesTest.add(new TicketType(4L,"Daily pass","Daily pass to use any service (Bus, Tram, Underground, Ship). Valid only purchase day.", 1500L));

        ticketTypesTest.forEach(ticketType -> ticketRepository.save(ticketType));
        List<TicketType> ticketTypes = ticketRepository.findAll();

        assertEquals(4, ticketTypes.size());
    }


    @Test
    public void emptyTable() throws Exception {

        List<TicketType> ticketTypes = ticketRepository.findAll();

        assertEquals(0, ticketTypes.size());
    }
}