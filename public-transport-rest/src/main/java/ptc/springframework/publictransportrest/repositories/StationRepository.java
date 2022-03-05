package ptc.springframework.publictransportrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ptc.springframework.publictransportrest.entities.Station;

import java.util.UUID;

public interface StationRepository extends JpaRepository<Station, UUID>, JpaSpecificationExecutor<Station> {
}
