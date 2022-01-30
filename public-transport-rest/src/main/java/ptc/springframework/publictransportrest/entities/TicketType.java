package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotNull
    private String name;

//    @NotNull
//    private String description;
//
//    @NotNull
//    @Min(1)
//    private Integer price;
//
//    @NotNull
//    private Integer expirationTime;
//
//    private UUID createdBy;
//
//    private LocalDateTime createdOn;
//
//    private UUID modifiedBy;
//
//    private LocalDateTime modifiedOn;

}
