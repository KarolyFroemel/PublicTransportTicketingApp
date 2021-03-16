package ptc.springframework.publictransportrest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    private UUID id;

    @NonNull
    @Size(max = 50)
    private String name;

    @NonNull
    @Size(max = 250)
    private String description;

    @NonNull
    @Min(1)
    private Long price;

    @OneToMany(mappedBy = "ticketType", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @NonNull
    private Long expirationTime;

}
