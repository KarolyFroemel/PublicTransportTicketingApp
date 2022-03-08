package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ptc.springframework.publictransportrest.enums.TicketHistoryType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tickets_history")
public class TicketHistory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    private TicketHistoryType historyType;

    @NotNull
    private UUID ticketId;

    @NotNull
    private UUID userId;

    private UUID accountId;

    @NotNull
    private UUID accountHistoryId;

    @NotNull
    private LocalDateTime createdOn;
}
