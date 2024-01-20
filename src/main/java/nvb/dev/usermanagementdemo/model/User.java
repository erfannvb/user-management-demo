package nvb.dev.usermanagementdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "firstName cannot be empty")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "lastName cannot be empty")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "username cannot be empty")
    @Column(name = "username", nullable = false)
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Column(name = "password", nullable = false)
    private String password;

    @Range(min = 18, max = 55)
    @Column(name = "age", nullable = false)
    private int age;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Account> accountSet = new HashSet<>();

}
