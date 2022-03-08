package ptc.springframework.publictransportrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="ticket_type_id", referencedColumnName = "id", nullable=false)
    @JsonIgnore
    private TicketType ticketType;

    @NotNull
    private LocalDateTime purchaseDate;

    @NotNull
    private LocalDateTime validFrom;

    @NotNull
    private LocalDateTime validTo;

    private LocalDateTime validationTime;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

}
