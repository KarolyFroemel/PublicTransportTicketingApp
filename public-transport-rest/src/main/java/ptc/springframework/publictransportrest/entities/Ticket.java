package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;
import ptc.springframework.publictransportrest.enums.TicketStatus;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tickets", schema="public_transport")
public class Ticket {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @NotNull
    private UUID ticketTypeId;

    @NotNull
    private LocalDateTime purchaseDate;

    @NotNull
    private LocalDateTime validFrom;

    @NotNull
    private LocalDateTime validTo;

    private LocalDateTime validationTime;

    private TicketStatus status;

}
