package ptc.springframework.publictransportrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ptc.springframework.publictransportrest.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
