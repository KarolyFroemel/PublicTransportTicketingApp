package ptc.springframework.publictransportrest.entities;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

//@Getter
//@Setter
//@Entity
//@Table(name = "accounts")
public class Account {

    @Id
    private UUID id;

    @NotNull
    private UUID userId;

    @NotNull
    private Long balance;

    @NotNull
    private Boolean locked;
}
