package ptc.springframework.publictransportrest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.testdata.TicketTypeTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TicketTypeRepositoryTest {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Test
    @DirtiesContext //TODO: azert ennek meg utananezni, hogy helyesen hasznaljuk
    public void findAllticketTypes() {
        List<TicketType> ticketTypesTest = TicketTypeTestData.getTicketTypeList();

        ticketTypesTest.forEach(ticketType -> ticketTypeRepository.save(ticketType));
        List<TicketType> ticketTypes = ticketTypeRepository.findAll();

        assertEquals(3, ticketTypes.size());
    }


    @Test
    @DirtiesContext
    public void emptyTicketTypesTable() {

        List<TicketType> ticketTypes = ticketTypeRepository.findAll();

        assertEquals(0, ticketTypes.size());
    }

    @Test
    @DirtiesContext
    public void findByName() {
        TicketType ticketType = TicketTypeTestData.getSingleTicketType();
        ticketTypeRepository.save(ticketType);

        TicketType ticketTypeResult = ticketTypeRepository.findByName(ticketType.getName()).get();

        assertEquals(ticketType.getName(),ticketTypeResult.getName());

    }
}