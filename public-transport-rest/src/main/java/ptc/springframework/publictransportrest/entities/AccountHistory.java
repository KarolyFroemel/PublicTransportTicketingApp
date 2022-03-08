package ptc.springframework.publictransportrest.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ptc.springframework.publictransportrest.enums.AccountHistoryTransactionType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounts_history")
public class AccountHistory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    private UUID accountId;

    @NotNull
    private AccountHistoryTransactionType transactionType;

    @NotNull
    private Long balanceBefore;

    @NotNull
    private Long balanceAfter;

    @NotNull
    private LocalDateTime createdOn;
}
