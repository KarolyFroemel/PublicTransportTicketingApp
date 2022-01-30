package ptc.springframework.publictransportrest.entities;

import ptc.springframework.publictransportrest.enums.ServiceType;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

//@Getter
//@Setter
//@Entity
//@Table(name = "services")
public class Service {

    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private ServiceType type;

    private UUID createdBy;

    private LocalDateTime createdOn;

    private UUID modifiedBy;

    private LocalDateTime modifiedOn;
}
