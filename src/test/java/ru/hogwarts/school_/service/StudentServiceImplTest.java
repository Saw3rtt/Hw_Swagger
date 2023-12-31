package ru.hogwarts.school_.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school_.exception.StudentException;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create_ValidStudent_CreatedStudent() {
        Student student = new Student(null, "Harry Potter", 17, null);
        when(studentRepository.findByNameAndAge("Harry Potter", 17)).thenReturn(Optional.empty());
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.create(student);
        assertEquals("Harry Potter", result.getName());
        assertEquals(17, result.getAge());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void create_DuplicateStudent_ThrowsException() {
        Student student = new Student(null, "Harry Potter", 17, null);
        when(studentRepository.findByNameAndAge("Harry Potter", 17)).thenReturn(Optional.of(student));

        StudentException ex = assertThrows(StudentException.class, () -> studentService.create(student));

        assertEquals("Такой студент уже существует!", ex.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void read_ExistingStudent_ReturnsStudent() {
        Student student = new Student(1L, "Hermione Granger", 18, null);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.read(1L);

        assertEquals(1L, result.getId());
        assertEquals("Hermione Granger", result.getName());
        assertEquals(18, result.getAge());
    }

    @Test
    void read_NonExistingStudent_ThrowsException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        StudentException ex = assertThrows(StudentException.class, () -> studentService.read(1L));

        assertEquals("Студент не найден", ex.getMessage());
    }

    @Test
    void update_ExistingStudent_UpdatedStudent() {
        Student student = new Student(1L, "Ron Weasley", 18, null);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        student.setName("Valera");
        student.setAge(19);
        Student updatedStudent = studentService.update(student);

        assertEquals(1L, updatedStudent.getId());
        assertEquals("Valera", updatedStudent.getName());
        assertEquals(19, updatedStudent.getAge());
    }

    @Test
    void update_NonExistingStudent_ThrowsException() {
        Student student = new Student(1L, "Neville Longbottom", 17, null);
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        StudentException ex = assertThrows(StudentException.class, () -> studentService.update(student));

        assertEquals("Студент не найден", ex.getMessage());
    }

    @Test
    void delete_ExistingStudent_DeletedStudent() {
        Student student = new Student(1L, "Luna Lovegood", 17, null);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student deletedStudent = studentService.delete(1L);
        assertEquals(1L, deletedStudent.getId());
    }

    @Test
    void readAll_ExistingAge_ReturnMatchingStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Draco Malfoy", 17, null));
        students.add(new Student(2L, "Cedric Diggory", 18, null));
        students.add(new Student(3L, "Nymphadora Tonks", 19, null));

        when(studentRepository.findByAge(17)).thenReturn(students);

        List<Student> result = studentService.readAll(17);

        assertEquals(3, result.size());
        assertEquals(17, result.get(0).getAge());
    }

    @Test
    void readAll_NonExistingAge_ReturnEmptyList() {
        when(studentRepository.findByAge(16)).thenReturn(new ArrayList<>());

        List<Student> result = studentService.readAll(16);

        assertEquals(0, result.size());
    }

    @Test
    public void findStudents_findStudentsByAge_returnStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Harry", 15, null));
        students.add(new Student(2L, "Germiona", 20, null));
        students.add(new Student(3L, "Ron", 21, null));

        when(studentRepository.findByAgeBetween(18, 22)).thenReturn(students);

        List<Student> result = studentService.findStudentsByAge(18, 22);

        assertEquals(3, result.size());
    }

    @Test
    void getStudentFaculty_StudentFound_ReturnsFaculty() {
        Long studentId = 1L;
        Student student = new Student(studentId, "Harry", 25,
                new Faculty(1L, "Hufflepuff", "Blue"));
        when(studentRepository.findStudentById(studentId)).thenReturn(Optional.of(student));

        Faculty faculty = studentService.getStudentFaculty(studentId);
        assertEquals(student.getFaculty(), faculty);

    }

    @Test
    void getStudentFaculty_StudentNotFound_ThrowsException() {
        Long studentId = 1L;
        when(studentRepository.findStudentById(studentId)).thenReturn(Optional.empty());

        assertThrows(StudentException.class, () -> studentService.getStudentFaculty(studentId));
    }

    @Test
    public void testFindStudentCount() {
        when(studentRepository.findStudentCount()).thenReturn(10L);

        Long result = studentService.findStudentCount();

        assertEquals(10L, result);
        verify(studentRepository, times(1)).findStudentCount();
    }

    @Test
    public void testFindAverageAge() {
        when(studentRepository.findAverageAge()).thenReturn(25);

        Integer result = studentService.findAverageAge();

        assertEquals(25, result);
        verify(studentRepository, times(1)).findAverageAge();
    }

    @Test
    public void testFindFiveLastStudent() {
        List<Student> students = Arrays.asList(
                new Student(1L, "John", 20, null),
                new Student(2L, "Alice", 22, null),
                new Student(3L, "Bob", 24, null),
                new Student(4L, "Eve", 26, null),
                new Student(5L, "Charlie", 28, null)
        );
        when(studentRepository.findLastStudent(5)).thenReturn(students);

        List<Student> result = studentService.findFiveLastStudent();

        assertEquals(5, result.size());
        verify(studentRepository, times(1)).findLastStudent(5);
    }
}

