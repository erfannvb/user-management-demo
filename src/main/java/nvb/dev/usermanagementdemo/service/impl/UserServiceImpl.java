package nvb.dev.usermanagementdemo.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.usermanagementdemo.exception.EntityNotFoundException;
import nvb.dev.usermanagementdemo.exception.NoDataFoundException;
import nvb.dev.usermanagementdemo.model.User;
import nvb.dev.usermanagementdemo.model.dto.UserDTO;
import nvb.dev.usermanagementdemo.repository.UserRepository;
import nvb.dev.usermanagementdemo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDTO findUserById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> new UserDTO(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getAge()))
                .orElseThrow(() ->
                        new EntityNotFoundException(User.class, userId));
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) throw new NoDataFoundException();
        return userList.stream().map(user -> new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getAge()
        )).toList();
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return unwrapUser(optionalUser, 404L);
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
            throw new EntityNotFoundException(User.class, userId);
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException(User.class, userId);
        }
    }

    @Override
    public void deleteAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) throw new NoDataFoundException();
        userRepository.deleteAll();
    }

    private User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(User.class, id);
    }
}
