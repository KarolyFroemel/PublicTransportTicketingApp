package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.UserRegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.config.MapstructConfig;
import ptc.springframework.publictransportrest.entity.User;

@Mapper(config = MapstructConfig.class)
public interface UserRegisterMapper {

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="createdBy", ignore = true),
            @Mapping(target="createdOn", ignore = true),
            @Mapping(target="modifiedBy", ignore = true),
            @Mapping(target="modifiedOn", ignore = true),
            @Mapping(target="role", ignore = true),
            @Mapping(target="numberOfIncorrectLogins", ignore = true),
            @Mapping(target="locked", ignore = true),
            @Mapping(target="token", ignore = true)
    })
    User UserRegisterModelToUser(UserRegisterModel userRegisterModel);
}
