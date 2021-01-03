package ptc.springframework.publictransportrest.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
    @GeneratedValue
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "id", updatable = false, nullable = false)
//    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
    private UUID id;

    @NonNull
    @Size(max = 250)
    private String name;

    @NonNull
    @Size(max = 250)
    private String email;

    @Size(max = 250)
    private String password;

    @Min(0)
    private Long balance;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Ticket> tickets = new ArrayList<>();
}
