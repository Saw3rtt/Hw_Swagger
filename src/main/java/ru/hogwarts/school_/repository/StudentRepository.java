package ru.hogwarts.school_.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameAndAge(String name, int age);

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);


}
