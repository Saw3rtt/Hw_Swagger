package ru.hogwarts.school_.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;

import java.util.List;
import java.util.Optional;


public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByNameAndColor(String name, String color);

    Optional<Faculty> findById(long id);

    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    List<Faculty> findByColor(String color);

    @Query("SELECT s FROM Student s WHERE s.faculty.id = :facultyId")
    List<Student> getStudentsByFaculty(@Param("facultyId") long facultyId);

}
