package ptc.springframework.publictransportrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ptc.springframework.publictransportrest.model.Ticket;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
