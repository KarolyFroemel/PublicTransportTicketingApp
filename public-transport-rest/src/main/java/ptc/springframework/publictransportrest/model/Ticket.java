package ptc.springframework.publictransportrest.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="ticket_type_id", nullable=false)
    private TicketType ticketType;

    @NonNull
    private Date purchaseDate;

    @NonNull
    private Date validationDate;

    @NonNull
    private Date consumable;

}
