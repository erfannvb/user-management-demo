package nvb.dev.usermanagementdemo.service;

import nvb.dev.usermanagementdemo.model.User;
import nvb.dev.usermanagementdemo.dto.UserDTO;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    UserDTO findUserById(Long userId);

    List<UserDTO> findAllUsers();

    User findUserByUsername(String username);

    User updateUser(User user, Long userId);

    void deleteUserById(Long userId);

    void deleteAllUsers();

}
