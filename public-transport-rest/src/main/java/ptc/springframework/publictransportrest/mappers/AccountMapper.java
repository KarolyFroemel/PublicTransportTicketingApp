package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.AccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.entities.Account;


@Mapper(config = MapstructConfig.class)
public interface AccountMapper {
    //TODO: a mapper szar
    @Mappings({
            @Mapping(target = "balance", ignore = true)
    })
    AccountModel accountEntityToAccountModel(Account account);
}
