package ptc.springframework.publictransportrest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc.springframework.publictransportrest.model.TicketType;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<TicketType, UUID> {
}
