package ptc.springframework.publictransportrest.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ptc.springframework.publictransportrest.entities.Station;
import ptc.springframework.publictransportrest.exceptions.StationException;
import ptc.springframework.publictransportrest.mappers.StationMapper;
import ptc.springframework.publictransportrest.repositories.StationRepository;

import java.util.UUID;

import static ptc.springframework.publictransportrest.exceptions.error.StationErrorCode.STATION_NOT_FOUND;

@Service
public class StationService {

    private final StationMapper stationMapper;

    private final StationRepository stationRepository;

    public StationService(StationMapper stationMapper,
                          StationRepository stationRepository) {
        this.stationMapper = stationMapper;
        this.stationRepository = stationRepository;
    }

    public Station getStationById(UUID id) {
        return stationRepository.findById(id).orElseThrow(
                () -> new StationException(
                        STATION_NOT_FOUND,
                        "Station not found.",
                        "Station not found in database",
                        HttpStatus.NOT_FOUND
                )
        );
    }
}
