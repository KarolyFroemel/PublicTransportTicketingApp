package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.AccountModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.entities.Account;


@Mapper(config = MapstructConfig.class)
public interface AccountMapper {

    AccountModel accountEntityToAccountModel(Account account);
}
