package nvb.dev.usermanagementdemo.mapper;

import nvb.dev.usermanagementdemo.dto.UserDTO;
import nvb.dev.usermanagementdemo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.usermanagementdemo.MotherObject.anyValidUser;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserMapperImpl.class})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void userToUserDTO() {
        UserDTO userDTO = userMapper.userToUserDTO(anyValidUser());

        assertEquals(anyValidUser().getFirstName(), userDTO.getUserFirstName());
        assertEquals(anyValidUser().getUsername(), userDTO.getUsername());
    }

    @Test
    void userToUserDTO_Null() {
        UserDTO userDTO = userMapper.userToUserDTO(null);

        assertNull(userDTO);
    }
}