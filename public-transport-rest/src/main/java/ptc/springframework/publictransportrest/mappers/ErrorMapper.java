package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.ErrorModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.exceptions.ErrorResponse;

@Mapper
public interface ErrorMapper {

    ErrorModel ErrorResponseToErrorModel(ErrorResponse errorResponse);
}
