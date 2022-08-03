package ca.tetervak.studentdata.data.jpa;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@Getter
@Setter
public class UserEntityJpa {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName = "";

    @Column(name = "password", nullable = false)
    private String password = "";

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="app_user_role",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private List<RoleEntityJpa> roles;
}
