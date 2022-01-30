package ptc.springframework.publictransportrest.entities;


import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

//@Getter
//@Setter
//@Entity
//@Table(name = "stations")
public class Station {

    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String qrCode;

    private UUID createdBy;

    private LocalDateTime createdOn;

    private UUID modifiedBy;

    private LocalDateTime modifiedOn;
}
