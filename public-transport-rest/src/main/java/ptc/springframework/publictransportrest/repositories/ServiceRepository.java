package ptc.springframework.publictransportrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ptc.springframework.publictransportrest.entities.PTCService;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<PTCService, UUID>, JpaSpecificationExecutor<PTCService> {
}
