package ptc.springframework.publictransportrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ptc.springframework.publictransportrest.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
