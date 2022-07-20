package ca.tetervak.studentdata.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepositoryJpa extends JpaRepository<UserEntityJpa, Integer> {

    UserEntityJpa findUserEntityJpaByUserNameIs(String userName);

    void deleteUserEntityJpaByUserNameIs(String useName);
}
