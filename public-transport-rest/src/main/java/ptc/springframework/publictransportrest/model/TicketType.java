package ptc.springframework.publictransportrest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Size(max = 50)
    private String name;

    @NonNull
    @Size(max = 250)
    private String description;

    @NonNull
    @Min(1)
    private Long price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tickets",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "ticket_type_id")})
    private List<User> users = new ArrayList<>();
}
