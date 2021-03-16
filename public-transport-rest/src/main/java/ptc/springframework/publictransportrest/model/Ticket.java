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
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="ticket_type_id", referencedColumnName = "id" ,nullable=false)
    private TicketType ticketType;

    @NonNull
    private LocalDateTime purchaseDate;

    @NonNull
    private LocalDateTime validFrom;

    @NonNull
    private LocalDateTime validTo;

    private LocalDateTime validationDate;

    public boolean isTicketValidated() {
         return this.validationDate == null ? false : true;
    }

    public boolean isTicketExpired() {
        return LocalDateTime.now().isAfter(validTo);
    }

    public boolean isPassValidationStarted() {
        return LocalDateTime.now().isAfter(validFrom);
    }

}
