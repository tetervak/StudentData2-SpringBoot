package ca.tetervak.studentdata.data.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_role")
@Data
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
