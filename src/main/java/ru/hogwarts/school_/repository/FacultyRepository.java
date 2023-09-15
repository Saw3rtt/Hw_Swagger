package ru.hogwarts.school_.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_.model.Faculty;

import java.util.List;
import java.util.Optional;


public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByNameAndColor(String name, String color);

    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    List<Faculty> findByColor(String color);
}
