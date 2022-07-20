package ca.tetervak.studentdata.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepositoryJpa extends JpaRepository<UserEntityJpa, Integer> {

    UserEntityJpa findUserEntityByUserNameIs(String userName);

    void deleteUserEntityByUserNameIs(String useName);
}
