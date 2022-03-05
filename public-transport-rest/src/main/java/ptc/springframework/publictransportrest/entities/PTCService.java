package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ptc.springframework.publictransportrest.enums.ServiceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "services", schema="public_transport")
public class PTCService {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull(message = "Name cannot be null in Service entity!")
    private String name;

    @NotNull(message = "Service type cannot be null in Service entity!")
    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "services_stations",
            joinColumns = {@JoinColumn(name = "services_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "stations_id", referencedColumnName = "id")}
    )
    private Set<Station> stations;

    private UUID createdBy;

    private LocalDateTime createdOn;

    private UUID modifiedBy;

    private LocalDateTime modifiedOn;
}
