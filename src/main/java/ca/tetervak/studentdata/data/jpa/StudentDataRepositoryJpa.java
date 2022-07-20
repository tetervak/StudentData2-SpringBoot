package ca.tetervak.studentdata.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDataRepositoryJpa extends JpaRepository<StudentEntityJpa, Integer> {
}
