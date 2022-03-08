package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounts", schema="public_transport")
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    private Integer balance;

    public void addBalance(int addToBalance) {
        this.balance += addToBalance;
    }

    public boolean checkPayingCapacity(int price) {
        return this.balance - price >= 0;
    }

    public void deductFee(int price) {
        this.balance-=price;
    }
}
