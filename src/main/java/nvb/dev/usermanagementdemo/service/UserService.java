package nvb.dev.usermanagementdemo.service;

import nvb.dev.usermanagementdemo.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User findUserById(Long userId);

    List<User> findAllUsers();

    User findUserByUsername(String username);

    User updateUser(User user, Long userId);

    void deleteUserById(Long userId);

    void deleteAllUsers();

}
