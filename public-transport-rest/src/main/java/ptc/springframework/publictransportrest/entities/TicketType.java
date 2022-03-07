package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ticket_types", schema="public_transport")
public class TicketType {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    protected UUID id;

    @NotNull
    private String name;

    private String description;

    private Integer price;

    private Integer expirationTime;

    @Column(name = "imgsource")
    private String imgSource;

    private UUID createdBy;

    private LocalDateTime createdOn;

    private UUID modifiedBy;

    private LocalDateTime modifiedOn;

    @OneToMany(mappedBy = "ticketType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> tickets;

}
