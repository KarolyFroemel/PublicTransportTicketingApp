package ptc.springframework.publictransportrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ptc.springframework.publictransportrest.entities.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
