package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.ErrorModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.exception.ErrorResponse;

@Mapper
public interface ErrorMapper {

    ErrorModel ErrorResponseToErrorModel(ErrorResponse errorResponse);
}
