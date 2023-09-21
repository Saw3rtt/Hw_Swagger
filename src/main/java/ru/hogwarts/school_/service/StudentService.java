package ru.hogwarts.school_.service;

import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;

import java.util.List;


public interface StudentService {
    Student create(Student student);

    Student read(long id);

    Student update(Student student);

    Student delete(long id);


    List<Student> readAll(int age);

    List<Student> findStudentsByAge(int min, int max);

    Faculty getStudentFaculty(long studentId);

    Long findStudentCount();

    Integer findAverageAge();

    List<Student> findFiveLastStudent();
}
