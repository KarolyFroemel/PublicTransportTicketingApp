package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.TicketModel;
import contract.ticket.model.TicketTypeModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ptc.springframework.publictransportrest.helper.DateTimeFormatterHelper;
import ptc.springframework.publictransportrest.model.Ticket;
import ptc.springframework.publictransportrest.model.TicketType;
import ptc.springframework.publictransportrest.model.User;
import ptc.springframework.publictransportrest.testdata.TicketTestData;
import ptc.springframework.publictransportrest.testdata.TicketTypeTestData;
import ptc.springframework.publictransportrest.testdata.UserTestData;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TicketMapperTest {

    TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);

    @Test
    void toTicketTypeModel() {
        TicketType ticketType = TicketTypeTestData.getSingleTicketType();
        TicketTypeModel ticketTypeModel = ticketMapper.toTicketTypeModel(ticketType);

        assertThat(ticketTypeModel.getName(),is(ticketType.getName()));
        assertThat(ticketTypeModel.getDescription(),is(ticketType.getDescription()));
        assertThat(ticketTypeModel.getPrice(),is(ticketType.getPrice().intValue()));
    }

    @Test
    void toTicketModel() {
        User user = UserTestData.getDavidUser();
        Ticket ticket = TicketTestData.getSingleTicket(user);
        TicketModel ticketModel = ticketMapper.toTicketModel(ticket);

        assertThat(ticketModel.getId(),is(ticket.getId()));
        assertThat(ticketModel.getName(),is(ticket.getTicketType().getName()));
        assertThat(ticketModel.getPurchaseDate().toString(),is(DateTimeFormatterHelper.parseTimestamp(ticket.getPurchaseDate())));
        assertThat(ticketModel.getCanBeUsed().toString(),is(DateTimeFormatterHelper.parseTimestamp(ticket.getCanBeUsed())));
        assertNull(ticketModel.getValidationDate());
    }

    @Test
    void toTicketTypeModelList() {
        List<TicketType> ticketTypeList = TicketTypeTestData.getTicketTypeList();
        List<TicketTypeModel> ticketTypeModelList = ticketMapper.toTicketTypeModelList(ticketTypeList);

        assertEquals(ticketTypeList.size(), ticketTypeModelList.size());
    }

    @Test
    void toTicketModelList() {
        User user = UserTestData.getDavidUser();
        List<Ticket> ticketList = TicketTestData.getTicketList(user);
        List<TicketModel> ticketModelList = ticketMapper.toTicketModelList(ticketList);

        assertEquals(ticketList.size(), ticketModelList.size());

    }
}