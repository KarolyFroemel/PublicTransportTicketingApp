package ptc.springframework.publictransportrest.entity;

import lombok.Getter;
import lombok.Setter;
import ptc.springframework.publictransportrest.enums.TransactionType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounts_history")
public class AccountHistory {

    @Id
    private UUID id;

    @NotNull
    private UUID accountId;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private Long balanceBefore;

    @NotNull
    private Long balanceAfter;

    @NotNull
    private LocalDateTime createdOn;
}
