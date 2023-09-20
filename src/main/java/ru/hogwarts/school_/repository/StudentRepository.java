package ru.hogwarts.school_.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameAndAge(String name, int age);

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    @Query("SELECT s.faculty FROM Student s WHERE s.id = :studentId")
    Optional<Faculty> getFacultyByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT count(student) from Student student")
    Long findStudentCount();

    @Query("SELECT avg(student.age) from Student student")
    Integer findAverageAge();

    @Query(value = "SELECT * from Student student order by student.id desc limit :size", nativeQuery = true)
    List<Student> findLastStudent(int size);

}
