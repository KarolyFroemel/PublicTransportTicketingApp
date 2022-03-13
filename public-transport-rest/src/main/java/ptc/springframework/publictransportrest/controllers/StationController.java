package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.StationApi;
import contract.ticket.model.CreateStationModel;
import contract.ticket.model.StationModel;
import contract.ticket.model.StationSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ptc.springframework.publictransportrest.services.StationService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class StationController implements StationApi {

    @Autowired
    private StationService stationService;

    @Override
    public ResponseEntity<StationModel> createNewStation(@Valid @RequestBody CreateStationModel createStationModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stationService.createNewStation(createStationModel));
    }

    @Override
    public ResponseEntity<Void> deleteStationById(UUID id) {
        stationService.deleteStationById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<StationModel> getStationById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(stationService.getStationModelById(id));
    }

    @Override
    public ResponseEntity<List<StationModel>> searchStation(Integer xPage, Integer xSize, @Valid StationSearchModel stationSearchModel) {
        Page<StationModel> page = stationService.searchStation(
                xPage,
                xSize,
                stationSearchModel);

        return ControllerHelper.buildPartialResponse(xPage, xSize, page);
    }

    @Override
    public ResponseEntity<Void> updateStation(@Valid StationModel stationModel) {
        stationService.updateStation(stationModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
