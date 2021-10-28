package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    @Min(1)
    private Integer price;

    @NotNull
    private Integer expirationTime;

    private UUID createdBy;

    private LocalDateTime createdOn;

    private UUID modifiedBy;

    private LocalDateTime modifiedOn;

}
