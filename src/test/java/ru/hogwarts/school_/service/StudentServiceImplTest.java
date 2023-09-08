package ru.hogwarts.school_.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school_.exception.StudentException;
import ru.hogwarts.school_.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    StudentServiceImpl underTest = new StudentServiceImpl();

    @Test
    void create_createStudent_returnedStudent() {
        Student student = new Student(null, "Harry Potter", 11);
        Student result = underTest.create(student);
        assertEquals(result, student);
    }

    @Test
    void create_student_thrownException() {
        Student student = new Student(null, "Hermione Granger", 12);
        underTest.create(student);

        StudentException ex = assertThrows(StudentException.class, () -> underTest.create(student));
        assertEquals("Такой студент уже существует!", ex.getMessage());
    }

    @Test
    void read_existingStudent_returnedStudent() {
        Student student = new Student(null, "Ron Weasley", 11);
        underTest.create(student);

        Student result = underTest.read(student.getId());

        assertEquals(student.getId(), result.getId());
        assertEquals("Ron Weasley", result.getName());
        assertEquals(11, result.getAge());
    }

    @Test
    void read_nonExistingStudent_thrownException() {
        StudentException ex = assertThrows(StudentException.class, () -> underTest.read(1L));
        assertEquals("Студент не найден", ex.getMessage());
    }

    @Test
    void update_existingStudent_updatedStudent() {
        Student student = new Student(null, "Draco Malfoy", 12);
        underTest.create(student);

        student.setName("NewName");
        student.setAge(13);
        Student updatedStudent = underTest.update(student);

        assertEquals(student.getId(), updatedStudent.getId());
        assertEquals("NewName", updatedStudent.getName());
        assertEquals(13, updatedStudent.getAge());
    }

    @Test
    void update_nonExistingStudent_thrownException() {
        Student student = new Student(1L, "Luna Lovegood", 14);
        StudentException ex = assertThrows(StudentException.class, () -> underTest.update(student));
        assertEquals("Студент не найден", ex.getMessage());
    }

    @Test
    void delete_existingStudent_deletedStudent() {
        Student student = new Student(null, "Neville Longbottom", 11);
        underTest.create(student);

        Student deletedStudent = underTest.delete(student.getId());

        assertEquals(student.getId(), deletedStudent.getId());
    }


    @Test
    void readAll_existingAge_returnMatchingStudents() {
        underTest.create(new Student(null, "Ginny Weasley", 12));
        underTest.create(new Student(null, "Fred Weasley", 13));
        underTest.create(new Student(null, "George Weasley", 13));

        List<Student> age13Students = underTest.readAll(13);

        assertNotNull(age13Students);
        assertEquals(2, age13Students.size());
        assertTrue(age13Students.stream().allMatch(student -> student.getAge() == 13));
    }

    @Test
    void readAll_nonExistingAge_returnEmptyList() {
        underTest.create(new Student(null, "Lily Potter", 11));
        underTest.create(new Student(null, "James Potter", 12));

        List<Student> age14Students = underTest.readAll(14);

        assertNotNull(age14Students);
        assertEquals(0, age14Students.size());
    }
}