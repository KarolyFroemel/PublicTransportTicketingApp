package ptc.springframework.publictransportrest.entities;

import ptc.springframework.publictransportrest.enums.TransactionType;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

//@Getter
//@Setter
//@Entity
//@Table(name = "accounts_history")
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
