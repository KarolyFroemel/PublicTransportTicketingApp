package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.BalanceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface AccountMapper {

    @Mapping(target="balance", source="balance")
    BalanceModel toBalanceModel(Long balance);
}
