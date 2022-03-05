package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.ErrorModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.exceptions.ErrorResponse;

@Mapper(config = MapstructConfig.class)
public interface ErrorMapper {

    ErrorModel ErrorResponseToErrorModel(ErrorResponse errorResponse);
}
