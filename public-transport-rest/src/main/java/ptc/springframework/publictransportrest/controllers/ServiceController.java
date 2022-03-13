package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.ServiceApi;
import contract.ticket.model.CreateServiceModel;
import contract.ticket.model.ServiceModel;
import contract.ticket.model.ServiceSearchModel;
import contract.ticket.model.ServiceWithStationsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ptc.springframework.publictransportrest.services.PTCServiceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class ServiceController implements ServiceApi {

    @Autowired
    private PTCServiceService ptcServiceService;

    @Override
    public ResponseEntity<Void> addStationToService(UUID serviceId, UUID stationId) {
        ptcServiceService.addStationToService(serviceId, stationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<ServiceModel> createNewService(@Valid CreateServiceModel createServiceModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ptcServiceService.createNewService(createServiceModel));
    }

    @Override
    public ResponseEntity<Void> deleteServiceById(UUID id) {
        ptcServiceService.deleteServiceById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<ServiceWithStationsModel> getServiceById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ptcServiceService.getServiceById(id));
    }

    @Override
    public ResponseEntity<Void> removeStationFromService(UUID serviceId, UUID stationId) {
        ptcServiceService.removeStationFromService(serviceId, stationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<ServiceModel>> searchService(Integer xPage, Integer xSize, @Valid ServiceSearchModel serviceSearchModel) {
        Page<ServiceModel> page = ptcServiceService.searchService(
                xPage,
                xSize,
                serviceSearchModel);
        return ControllerHelper.buildPartialResponse(xPage, xSize, page);
    }

    @Override
    public ResponseEntity<Void> updateService(@Valid ServiceModel serviceModel) {
        ptcServiceService.updateService(serviceModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
