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
    private LocalDateTime purchaseDate;

    @NonNull
    private LocalDateTime validFrom;

    @NonNull
    private LocalDateTime validTo;

    private LocalDateTime validationDate;



}
