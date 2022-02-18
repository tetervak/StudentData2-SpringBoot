package ca.tetervak.studentdata.data.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

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
            name="user_role",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private List<RoleEntity> roles;

    public UserEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
