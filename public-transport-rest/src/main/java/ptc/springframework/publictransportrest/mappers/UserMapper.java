package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.UserHistoryModel;
import contract.ticket.model.UserModel;
import org.mapstruct.Mapper;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.entities.User;
import ptc.springframework.publictransportrest.entities.UserHistory;

@Mapper(uses = {
        AccountMapper.class
}, config = MapstructConfig.class)
public interface UserMapper {

    UserModel userEntityToUserModel(User user);

    UserHistoryModel userHistoryEntityToUserHistoryModel(UserHistory userHistory);

}
