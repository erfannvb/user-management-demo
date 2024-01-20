package nvb.dev.usermanagementdemo.mapper;

import nvb.dev.usermanagementdemo.dto.UserDTO;
import nvb.dev.usermanagementdemo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    UserDTO userToUserDTO(User user);

}
