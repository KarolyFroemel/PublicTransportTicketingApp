package ptc.springframework.publictransportrest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id" ,nullable=false)
    private User user;

    private LocalDateTime lastModification;

    @NonNull
    @Min(0)
    private Long balance;

    public void deductFee(Long fee) {
        this.balance  -= fee;
    }

    public void fillBalance(Long newBalance) {
        this.balance += newBalance;
    }
}
