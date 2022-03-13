package ptc.springframework.publictransportrest.testdata;

import contract.ticket.model.PurchaseTicketModel;
import contract.ticket.model.TicketSearchModel;

import java.time.LocalDate;
import java.util.UUID;

public class TicketTestData {

    private final UUID SINGLE_TICKET = UUID.fromString("dbb5103f-4bfe-42e3-b6ab-1ca7cbcde047");

    public PurchaseTicketModel getPurchaseTicketModel() {
        PurchaseTicketModel purchaseTicketModel = new PurchaseTicketModel();

        purchaseTicketModel.setTicketId(SINGLE_TICKET);
        purchaseTicketModel.setValidFrom(LocalDate.now());

        return purchaseTicketModel;
    }

    public PurchaseTicketModel getPurchaseTicketModelDateInThePast() {
        PurchaseTicketModel purchaseTicketModel = new PurchaseTicketModel();

        purchaseTicketModel.setTicketId(SINGLE_TICKET);
        purchaseTicketModel.setValidFrom(LocalDate.now().minusDays(1));

        return purchaseTicketModel;
    }

    public PurchaseTicketModel getPurchaseTicketModelNonExistingTicketType() {
        PurchaseTicketModel purchaseTicketModel = new PurchaseTicketModel();

        purchaseTicketModel.setTicketId(UUID.randomUUID());
        purchaseTicketModel.setValidFrom(LocalDate.now());

        return purchaseTicketModel;
    }

    public TicketSearchModel getTicketSearchModelASC() {
        TicketSearchModel ticketSearchModel = new TicketSearchModel();
        ticketSearchModel.setTicketType("Single ticket");
        ticketSearchModel.setSortBy("ticketTypeId");
        ticketSearchModel.setSortOrder(TicketSearchModel.SortOrderEnum.ASC);
        ticketSearchModel.setStatus(TicketSearchModel.StatusEnum.CAN_BE_USED);
        return ticketSearchModel;
    }

    public TicketSearchModel getTicketSearchModelDESC() {
        TicketSearchModel ticketSearchModel = new TicketSearchModel();
        ticketSearchModel.setTicketType("Single ticket");
        ticketSearchModel.setSortBy("ticketType");
        ticketSearchModel.setSortOrder(TicketSearchModel.SortOrderEnum.DESC);
        ticketSearchModel.setStatus(TicketSearchModel.StatusEnum.CAN_BE_USED);
        return ticketSearchModel;
    }
}
