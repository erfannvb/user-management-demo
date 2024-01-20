package nvb.dev.usermanagementdemo.mapper;

import nvb.dev.usermanagementdemo.dto.UserDTO;
import nvb.dev.usermanagementdemo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserMapperImpl.class})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void userToUserDTO() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("test");
        user.setLastName("test");
        user.setUsername("test");
        user.setPassword("test");
        user.setAge(30);

        UserDTO userDTO = userMapper.userToUserDTO(user);

        assertEquals(user.getFirstName(), userDTO.getUserFirstName());
        assertEquals(user.getUsername(), userDTO.getUsername());
    }

    @Test
    void userToUserDTO_Null() {
        UserDTO userDTO = userMapper.userToUserDTO(null);

        assertNull(userDTO);
    }
}