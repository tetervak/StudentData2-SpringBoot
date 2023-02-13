package ca.tetervak.studentdata.data.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_role")
@NoArgsConstructor
@Getter
@Setter
public class RoleEntityJpa {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName = "";

    @ManyToMany(mappedBy="roles")
    private List<UserEntityJpa> users;
}
