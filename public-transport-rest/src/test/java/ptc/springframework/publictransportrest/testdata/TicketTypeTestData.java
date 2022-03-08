package ptc.springframework.publictransportrest.testdata;

import contract.ticket.model.TicketTypeModel;
import contract.ticket.model.TicketTypeSearchRequestModel;

import java.util.UUID;

public class TicketTypeTestData {

    public TicketTypeModel getNewTicketTypeModel() {
        TicketTypeModel ticketTypeModel = new TicketTypeModel();
        ticketTypeModel.setId(null);
        ticketTypeModel.setName("new ticket type");
        ticketTypeModel.setDescription("New ticket type description");
        ticketTypeModel.setExpirationTime(30);
        ticketTypeModel.setPrice(50);
        ticketTypeModel.setIsEnforceable(true);
        return ticketTypeModel;
    }

    public TicketTypeSearchRequestModel getTicketTypeSearchRequestModelSortByNameASC() {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = new TicketTypeSearchRequestModel();
        ticketTypeSearchRequestModel.setName("ticket");
        ticketTypeSearchRequestModel.setDescription("one ride");
        ticketTypeSearchRequestModel.setSortBy("name");
        ticketTypeSearchRequestModel.setSortOrder(TicketTypeSearchRequestModel.SortOrderEnum.ASC);
        return ticketTypeSearchRequestModel;
    }

    public TicketTypeSearchRequestModel getTicketTypeSearchRequestModelSortByNameDesc() {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = getTicketTypeSearchRequestModelSortByNameASC();
        ticketTypeSearchRequestModel.setSortOrder(TicketTypeSearchRequestModel.SortOrderEnum.DESC);
        return ticketTypeSearchRequestModel;
    }

    public TicketTypeSearchRequestModel getTicketTypeSearchRequestModelSortByDescriptionASC() {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = getTicketTypeSearchRequestModelSortByNameASC();
        ticketTypeSearchRequestModel.setSortBy("description");
        return ticketTypeSearchRequestModel;
    }

    public TicketTypeSearchRequestModel getTicketTypeSearchRequestModelSortByDescriptionDESC() {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = getTicketTypeSearchRequestModelSortByDescriptionASC();
        ticketTypeSearchRequestModel.setSortOrder(TicketTypeSearchRequestModel.SortOrderEnum.DESC);
        return ticketTypeSearchRequestModel;
    }

    public TicketTypeSearchRequestModel getTicketTypeSearchRequestModelEmpty() {
        TicketTypeSearchRequestModel ticketTypeSearchRequestModel = getTicketTypeSearchRequestModelSortByNameASC();
        ticketTypeSearchRequestModel.setName("");
        ticketTypeSearchRequestModel.setDescription("");
        return ticketTypeSearchRequestModel;
    }

    public TicketTypeModel getSingleTicketTypeModel() {
        TicketTypeModel ticketTypeModel = new TicketTypeModel();
        ticketTypeModel.setId(UUID.fromString("dbb5103f-4bfe-42e3-b6ab-1ca7cbcde047"));
        ticketTypeModel.setName("Single ticket");
        ticketTypeModel.setDescription("Single ticket for one ride. Valid to 60 minutes from validation.");
        ticketTypeModel.setExpirationTime(365);
        ticketTypeModel.setPrice(1);
        ticketTypeModel.setIsEnforceable(true);
        return ticketTypeModel;
    }

    public TicketTypeModel getModifiedSingleTicketTypeModel() {
        TicketTypeModel ticketTypeModel = getSingleTicketTypeModel();
        ticketTypeModel.setName("modified ticket name");
        return ticketTypeModel;
    }

    public TicketTypeModel getNonExistingTicketTypeModel() {
        TicketTypeModel ticketTypeModel = getSingleTicketTypeModel();
        ticketTypeModel.setId(UUID.randomUUID());
        return ticketTypeModel;
    }
}
