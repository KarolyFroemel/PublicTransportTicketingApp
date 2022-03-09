package ptc.springframework.publictransportrest.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ptc.springframework.publictransportrest.enums.HistoryType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_history", schema="public_transport")
public class UserHistory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    private HistoryType historyType;

    private UUID ticketId;

    @NotNull
    private UUID userId;

    private Integer balanceBefore;

    private Integer balanceAfter;

    @NotNull
    private LocalDateTime createdOn;
}
