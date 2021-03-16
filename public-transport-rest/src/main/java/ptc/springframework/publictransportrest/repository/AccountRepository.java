package ptc.springframework.publictransportrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ptc.springframework.publictransportrest.exception.AccountNotFoundException;
import ptc.springframework.publictransportrest.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findById(UUID accountId) throws AccountNotFoundException;
}
