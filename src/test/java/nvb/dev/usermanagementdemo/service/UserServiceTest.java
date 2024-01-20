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

import static nvb.dev.usermanagementdemo.MotherObject.*;
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

        when(passwordEncoder.encode(anyValidUser().getPassword())).thenReturn("dummy");
        when(userRepository.save(any(User.class))).thenReturn(anyValidUser());

        User savedUser = userService.saveUser(anyValidUser());

        assertEquals("dummy", savedUser.getUsername());
        assertEquals("dummy", savedUser.getPassword());

        verify(passwordEncoder, atLeastOnce()).encode(anyValidUser().getPassword());
        verify(userRepository, atLeastOnce()).save(anyValidUser());
    }

    @Test
    void updateUserTest_ExistingUser() {
        long userId = 1L;

        User existingUser = anyValidUser();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");

        User updatedUser = anyValidUpdatedUser();
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

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(anyValidUpdatedUser(), userId));

        verify(userRepository, atLeastOnce()).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findUserByIdTest_ExistingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(anyValidUser()));
        when(userMapper.userToUserDTO(anyValidUser())).thenReturn(anyValidUserDTO());

        UserDTO foundUser = userService.findUserById(1L);

        assertEquals("dummy", foundUser.getUsername());
        verify(userRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void findUserByIdTest_NonExistingUser() {
        when(userRepository.findById(4L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(anyValidUser().getId()));
        verify(userRepository, atLeastOnce()).findById(anyValidUser().getId());
    }

    @Test
    void findAllUsersTest_ExistingUsers() {
        List<User> userList = List.of(anyValidUser(), anyValidUser());

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
        when(userRepository.findByUsername(anyValidUser().getUsername())).thenReturn(Optional.of(anyValidUser()));

        User foundUser = userService.findUserByUsername(anyValidUser().getUsername());

        assertEquals("dummy", foundUser.getUsername());
        verify(userRepository, atLeastOnce()).findByUsername(anyValidUser().getUsername());
    }

    @Test
    void findUserByUsernameTest_NonExistingUser() {
        when(userRepository.findByUsername(anyValidUser().getUsername())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                userService.findUserByUsername(anyValidUser().getUsername()));
        verify(userRepository, atLeastOnce()).findByUsername(anyValidUser().getUsername());
    }

    @Test
    void deleteUserByIdTest_ExistingUser() {
        when(userRepository.findById(anyValidUser().getId())).thenReturn(Optional.of(anyValidUser()));

        userService.deleteUserById(1L);

        verify(userRepository, atLeastOnce()).findById(1L);
        verify(userRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteUserByIdTest_NonExistingUser() {
        when(userRepository.findById(anyValidUser().getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUserById(anyValidUser().getId()));

        verify(userRepository, atLeastOnce()).findById(anyValidUser().getId());
        verify(userRepository, never()).deleteById(anyValidUser().getId());
    }

    @Test
    void deleteAllUsersTest_ExistingUsers() {
        List<User> userList = List.of(anyValidUser(), anyValidUser());

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