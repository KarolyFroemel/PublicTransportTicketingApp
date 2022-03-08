package ptc.springframework.publictransportrest.mappers;

import contract.ticket.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.configurations.MapstructConfig;
import ptc.springframework.publictransportrest.entities.User;

@Mapper(uses = {
        AccountMapper.class
}, config = MapstructConfig.class)
public interface UserMapper {

    //TODO: a mapper szar
    @Mappings({
            @Mapping(target = "account", ignore = true)
    })
    UserModel userEntityToUserModel(User user);
}
