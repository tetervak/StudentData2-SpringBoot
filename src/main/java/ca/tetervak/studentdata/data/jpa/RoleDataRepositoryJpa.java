package ca.tetervak.studentdata.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDataRepositoryJpa extends JpaRepository<RoleEntityJpa, Integer> {

    RoleEntityJpa findRoleEntityJpaByRoleNameIs(String roleName);
}
