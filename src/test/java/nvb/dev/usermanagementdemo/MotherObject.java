package nvb.dev.usermanagementdemo;

import nvb.dev.usermanagementdemo.dto.UserDTO;
import nvb.dev.usermanagementdemo.model.User;

public class MotherObject {

    public static final String ANY_STRING = "dummy";
    public static final String ANY_UPDATED_STRING = "dummy2";
    public static final int ANY_NUMBER = 30;
    public static final int ANY_UPDATED_NUMBER = 35;

    public static User anyValidUser() {
        return User.builder()
                .id(1L)
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .username(ANY_STRING)
                .password(ANY_STRING)
                .age(ANY_NUMBER)
                .build();
    }

    public static User anyValidUpdatedUser() {
        return User.builder()
                .id(1L)
                .firstName(ANY_UPDATED_STRING)
                .lastName(ANY_UPDATED_STRING)
                .username(ANY_UPDATED_STRING)
                .password(ANY_UPDATED_STRING)
                .age(ANY_UPDATED_NUMBER)
                .build();
    }

    public static UserDTO anyValidUserDTO() {
        return UserDTO.builder()
                .userId(1L)
                .userFirstName(ANY_STRING)
                .userLastName(ANY_STRING)
                .username(ANY_STRING)
                .age(ANY_NUMBER)
                .build();
    }

}
