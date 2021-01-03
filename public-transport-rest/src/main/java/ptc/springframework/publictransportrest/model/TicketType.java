package ptc.springframework.publictransportrest.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
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
    @GeneratedValue
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "id", updatable = false, nullable = false)
//    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
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

    @OneToOne(mappedBy = "ticketType", fetch = FetchType.LAZY)
    private Ticket tickets;
}
