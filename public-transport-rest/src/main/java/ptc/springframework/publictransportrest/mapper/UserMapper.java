package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ptc.springframework.publictransportrest.config.MapstructConfig;
import ptc.springframework.publictransportrest.entity.User;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {

    UserModel UserToUserModel(User user);

    @Mappings({
            @Mapping(target="createdBy", ignore = true),
            @Mapping(target="createdOn", ignore = true),
            @Mapping(target="modifiedBy", ignore = true),
            @Mapping(target="modifiedOn", ignore = true)
    })
    User UserModelToUser(UserModel userModel);
}
