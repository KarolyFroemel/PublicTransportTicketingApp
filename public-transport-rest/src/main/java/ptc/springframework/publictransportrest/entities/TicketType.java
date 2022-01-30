package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ticket_types", schema="ptc")
public class TicketType {

    @Id
    private UUID id;

    @NotNull
    private String name;

    private String description;

    private Integer price;

    private Integer expirationTime;

    private UUID createdBy;

    private LocalDateTime createdOn;

    private UUID modifiedBy;

    private LocalDateTime modifiedOn;

}
