package nvb.dev.usermanagementdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String username;
    private int age;

}
