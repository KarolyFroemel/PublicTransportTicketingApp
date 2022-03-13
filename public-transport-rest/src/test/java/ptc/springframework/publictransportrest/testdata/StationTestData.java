package ptc.springframework.publictransportrest.testdata;

import contract.ticket.model.CreateStationModel;
import contract.ticket.model.StationModel;
import contract.ticket.model.StationSearchModel;

import java.util.UUID;

public class StationTestData {

    public CreateStationModel getCreateStationModel(){
        CreateStationModel createStationModel = new CreateStationModel();
        createStationModel.setName("new station");
        return createStationModel;
    }

    public StationModel getResult(){
        StationModel stationModel = new StationModel();
        stationModel.setId(UUID.fromString("62838d89-8099-4bb0-a934-341d165a58d0"));
        stationModel.name("Big Ben");
        return stationModel;
    }

    public StationModel getStationModel() {
        StationModel stationModel = new StationModel();
        stationModel.setId(UUID.fromString("62838d89-8099-4bb0-a934-341d165a58d0"));
        stationModel.name("Big Ben modified");
        return stationModel;
    }

    public StationModel getStationModelNonExisting() {
        StationModel stationModel = new StationModel();
        stationModel.setId(UUID.randomUUID());
        stationModel.name("Big Ben modified");
        return stationModel;
    }

    public StationSearchModel getStationSearchModelASC() {
        StationSearchModel stationSearchModel = new StationSearchModel();
        stationSearchModel.setName("Big");
        stationSearchModel.setSortBy("name");
        stationSearchModel.setSortOrder(StationSearchModel.SortOrderEnum.ASC);
        return stationSearchModel;
    }

    public StationSearchModel getStationSearchModelDESC() {
        StationSearchModel stationSearchModel = new StationSearchModel();
        stationSearchModel.setName("Big");
        stationSearchModel.setSortBy("name");
        stationSearchModel.setSortOrder(StationSearchModel.SortOrderEnum.DESC);
        return stationSearchModel;
    }

    public StationSearchModel getStationSearchModelEmpty() {
        StationSearchModel stationSearchModel = new StationSearchModel();
        return stationSearchModel;
    }
}
