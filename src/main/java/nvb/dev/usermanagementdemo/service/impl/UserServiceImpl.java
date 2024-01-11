package nvb.dev.usermanagementdemo.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.usermanagementdemo.exception.NoDataFoundException;
import nvb.dev.usermanagementdemo.exception.UserNotFoundException;
import nvb.dev.usermanagementdemo.model.User;
import nvb.dev.usermanagementdemo.repository.UserRepository;
import nvb.dev.usermanagementdemo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public List<User> findAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) throw new NoDataFoundException();
        return userList;
    }

    @Override
    public User updateUser(User user, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();

            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            currentUser.setUsername(user.getUsername());
            currentUser.setAge(user.getAge());

            return userRepository.save(currentUser);
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public void deleteAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) throw new NoDataFoundException();
        userRepository.deleteAll();
    }
}
