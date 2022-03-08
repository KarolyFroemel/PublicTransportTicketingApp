package ptc.springframework.publictransportrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users", schema="public_transport")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    private Integer balance;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Ticket> ticketList;

    private UUID createdBy;

    private LocalDateTime createdOn;

    private UUID modifiedBy;

    private LocalDateTime modifiedOn;
}
