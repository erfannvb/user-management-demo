package nvb.dev.usermanagementdemo.service;

import nvb.dev.usermanagementdemo.exception.EntityNotFoundException;
import nvb.dev.usermanagementdemo.exception.NoDataFoundException;
import nvb.dev.usermanagementdemo.mapper.UserMapper;
import nvb.dev.usermanagementdemo.model.User;
import nvb.dev.usermanagementdemo.dto.UserDTO;
import nvb.dev.usermanagementdemo.repository.UserRepository;
import nvb.dev.usermanagementdemo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void saveUserTest() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("testPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals("testUser", savedUser.getUsername());
        assertEquals("testPassword", savedUser.getPassword());

        verify(passwordEncoder, atLeastOnce()).encode(user.getPassword());
        verify(userRepository, atLeastOnce()).save(user);
    }

    @Test
    void updateUserTest_ExistingUser() {
        long userId = 1L;

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setUsername("updatedUser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User resultUser = userService.updateUser(updatedUser, userId);

        assertEquals(updatedUser.getUsername(), resultUser.getUsername());

        verify(userRepository, atLeastOnce()).findById(userId);
        verify(userRepository, atLeastOnce()).save(existingUser);
    }

    @Test
    void updateUserTest_NonExistingUser() {
        Long userId = 1L;

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setUsername("updatedUser");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(updatedUser, userId));

        verify(userRepository, atLeastOnce()).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findUserByIdTest_ExistingUser() {
        User user = new User();
        user.setUsername("testUsername");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUsername");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        UserDTO foundUser = userService.findUserById(1L);

        assertEquals("testUsername", foundUser.getUsername());
        verify(userRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void findUserByIdTest_NonExistingUser() {
        User user = new User();

        when(userRepository.findById(4L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(user.getId()));
        verify(userRepository, atLeastOnce()).findById(user.getId());
    }

    @Test
    void findAllUsersTest_ExistingUsers() {
        List<User> userList = List.of(new User(), new User());

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> allUsers = userService.findAllUsers();

        assertEquals(2, allUsers.size());
        verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    void findAllUsersTest_NonExistingUsers() {
        when(userRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> userService.findAllUsers());
        verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    void findUserByUsernameTest_ExistingUser() {
        User user = new User();
        user.setUsername("john.doe");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User foundUser = userService.findUserByUsername(user.getUsername());

        assertEquals("john.doe", foundUser.getUsername());
        verify(userRepository, atLeastOnce()).findByUsername(user.getUsername());
    }

    @Test
    void findUserByUsernameTest_NonExistingUser() {
        User user = new User();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findUserByUsername(user.getUsername()));
        verify(userRepository, atLeastOnce()).findByUsername(user.getUsername());
    }

    @Test
    void deleteUserByIdTest_ExistingUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUserById(1L);

        verify(userRepository, atLeastOnce()).findById(1L);
        verify(userRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteUserByIdTest_NonExistingUser() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUserById(userId));

        verify(userRepository, atLeastOnce()).findById(userId);
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    void deleteAllUsersTest_ExistingUsers() {
        List<User> userList = List.of(new User(), new User());

        when(userRepository.findAll()).thenReturn(userList);

        userService.deleteAllUsers();

        verify(userRepository, atLeastOnce()).findAll();
        verify(userRepository, atLeastOnce()).deleteAll();
    }

    @Test
    void deleteAllUsersTest_NonExistingUsers() {
        when(userRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> userService.deleteAllUsers());

        verify(userRepository, atLeastOnce()).findAll();
        verify(userRepository, never()).deleteAll();
    }

}