package ptc.springframework.publictransportrest.testdata;

import contract.ticket.model.CreateServiceModel;
import contract.ticket.model.ServiceModel;
import contract.ticket.model.ServiceSearchModel;
import contract.ticket.model.StationModel;

import java.util.UUID;

public class ServiceTestData {
    public CreateServiceModel getCreateServiceModel(){
        CreateServiceModel createServiceModel = new CreateServiceModel();
        createServiceModel.setName("Test Service");
        createServiceModel.setType(CreateServiceModel.TypeEnum.SUBWAY);
        return createServiceModel;
    }

    public ServiceModel getServiceModelWithId(UUID id){
        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setId(id);
        serviceModel.setName("New Service Name");
        serviceModel.setType(ServiceModel.TypeEnum.SUBWAY);
        return serviceModel;
    }

    public ServiceSearchModel getSearchServiceModelASC(){
        ServiceSearchModel serviceSearchModel = new ServiceSearchModel();
        serviceSearchModel.setName("Bus");
        serviceSearchModel.setType(ServiceSearchModel.TypeEnum.BUS);
        serviceSearchModel.setSortBy("name");
        serviceSearchModel.setSortOrder(ServiceSearchModel.SortOrderEnum.ASC);
        return serviceSearchModel;
    }

    public ServiceSearchModel getSearchServiceModelDESC(){
        ServiceSearchModel serviceSearchModel = getSearchServiceModelASC();
        serviceSearchModel.setSortOrder(ServiceSearchModel.SortOrderEnum.DESC);
        return serviceSearchModel;
    }

    public ServiceSearchModel getSearchServiceModelEmpty(){
        ServiceSearchModel serviceSearchModel = new ServiceSearchModel();
        return serviceSearchModel;
    }

}
