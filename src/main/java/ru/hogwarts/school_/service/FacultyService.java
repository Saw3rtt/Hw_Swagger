package ru.hogwarts.school_.service;

import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;

import java.util.List;


public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty read(long id);

    Faculty update(Faculty faculty);

    Faculty delete(long id);

    List<Faculty> readAll(String color);

    List<Faculty> findNameOrColorIgnoreCase(String name, String color);

    List<Student> getListStudentsByFaculty(long facultyId);
}
