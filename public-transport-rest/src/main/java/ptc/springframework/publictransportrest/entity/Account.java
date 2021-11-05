package ptc.springframework.publictransportrest.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounts")
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
