package ptc.springframework.publictransportrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ptc.springframework.publictransportrest.entities.Station;

import java.util.UUID;

@Repository
public interface StationRepository extends JpaRepository<Station, UUID>, JpaSpecificationExecutor<Station> {
}
