package ptc.springframework.publictransportrest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Size(max = 250)
    private String name;

    @NonNull
    @Size(max = 250)
    private String email;

    @NonNull
    @Size(max = 250)
    private String password;

    @Min(0)
    private Long balance;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<TicketType> tickets;
}
