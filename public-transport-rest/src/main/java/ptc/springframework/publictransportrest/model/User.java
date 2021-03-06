package ptc.springframework.publictransportrest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id;

    @NonNull
    @Size(max = 250)
    private String name;

    @NonNull
    @Size(max = 250)
    private String email;

    @Size(max = 250)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Ticket> tickets = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;
}
