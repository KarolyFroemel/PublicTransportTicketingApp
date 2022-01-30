package ptc.springframework.publictransportrest.entities;

import ptc.springframework.publictransportrest.enums.TicketStatus;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

//@Getter
//@Setter
//@Entity
//@Table(name = "tickets")
public class Ticket {

    @Id
    private UUID id;

    @NotNull
    private UUID userId;

    @NotNull
    private UUID ticketTypeId;

    private LocalDateTime purchaseDate;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private LocalDateTime validationTime;

    private TicketStatus status;

}
