package nvb.dev.usermanagementdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nvb.dev.usermanagementdemo.model.User;
import nvb.dev.usermanagementdemo.model.dto.UserDTO;
import nvb.dev.usermanagementdemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1")
@Tag(name = "User Controller", description = "Performing CRUD Operation on Users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create User", description = "Creates a user from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of user"),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission")
    })
    @PostMapping(value = "/user/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by id", description = "Returns an user based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of user"),
            @ApiResponse(responseCode = "404", description = "User does not exist")
    })
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @Operation(summary = "Get all users", description = "Provides a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of users"),
            @ApiResponse(responseCode = "404", description = "Data Not Found")
    })
    @GetMapping(value = "/user/all")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Update user", description = "Updates a user based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful updating of user"),
            @ApiResponse(responseCode = "404", description = "User does not exist"),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission")
    })
    @PutMapping(value = "/user/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user, @PathVariable Long userId) {
        return new ResponseEntity<>(userService.updateUser(user, userId), HttpStatus.OK);
    }

    @Operation(summary = "Delete user by id", description = "Deletes a user based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of user"),
            @ApiResponse(responseCode = "404", description = "User does not exist")
    })
    @DeleteMapping(value = "/user/{userId}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all users", description = "Deletes all the existing users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of users"),
            @ApiResponse(responseCode = "404", description = "Users do not exist")
    })
    @DeleteMapping(value = "/user/all")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
