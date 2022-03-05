package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users", schema="public_transport")
public class User {

    @Id
    private UUID id;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    @Email
    private String email;

    private UUID createdBy;

    private LocalDateTime createdOn;

    private UUID modifiedBy;

    private LocalDateTime modifiedOn;
}
