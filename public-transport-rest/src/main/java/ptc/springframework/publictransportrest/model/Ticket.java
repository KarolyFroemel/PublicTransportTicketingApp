package ptc.springframework.publictransportrest.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToOne
    @JoinColumn(name="ticket_type_id", referencedColumnName = "id" ,nullable=false)
    private TicketType ticketType;

    @NonNull
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime purchaseDate;

//    @NonNull
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime validationDate;

    @NonNull
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime canBeUsed;

}
