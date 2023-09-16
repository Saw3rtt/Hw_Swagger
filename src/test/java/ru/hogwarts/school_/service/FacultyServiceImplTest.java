package ru.hogwarts.school_.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school_.exception.FacultyException;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacultyServiceImplTest {

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Mock
    private FacultyRepository facultyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_createFaculty_success() {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        when(facultyRepository.findByNameAndColor("Gryffindor", "Red")).thenReturn(Optional.empty());
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty createdFaculty = facultyService.create(faculty);

        assertEquals("Gryffindor", createdFaculty.getName());
        assertEquals("Red", createdFaculty.getColor());
    }

    @Test
    void create_duplicateFaculty_throwsException() {
        Faculty faculty = new Faculty(null, "Slytherin", "Green");


        when(facultyRepository.findByNameAndColor("Slytherin", "Green")).thenReturn(Optional.of(faculty));

        FacultyException ex = assertThrows(FacultyException.class, () -> facultyService.create(faculty));

        assertEquals("Такой факультет уже создан!", ex.getMessage());
        verify(facultyRepository, never()).save(any(Faculty.class));
    }

    @Test
    void read_ExistingFaculty_ReturnsFaculty() {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Faculty result = facultyService.read(1L);
        assertEquals(1L, result.getId());
        assertEquals("Gryffindor", result.getName());
        assertEquals("Red", result.getColor());
    }

    @Test
    void read_NonExistingFaculty_ThrowsException() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());

        FacultyException ex = assertThrows(FacultyException.class, () -> facultyService.read(1L));

        assertEquals("Факультет не найден", ex.getMessage());
    }

    @Test
    void update_ExistingFaculty_UpdatedFaculty() {
        Faculty faculty = new Faculty(1L, "Gryffindor", "red");
        when(facultyRepository.findById(1l)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        faculty.setName("Hufflepuff");
        faculty.setColor("Brown");
        Faculty updatedFaculty = facultyService.update(faculty);

        assertEquals(1L, updatedFaculty.getId());
        assertEquals("Hufflepuff", updatedFaculty.getName());
        assertEquals("Brown", updatedFaculty.getColor());
    }

    @Test
    void update_NonExistingFaculty_ThrowsException() {
        Faculty faculty = new Faculty(1L, "Hufflepuff", "Yellow");
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());

        FacultyException ex = assertThrows(FacultyException.class, () -> facultyService.update(faculty));

        assertEquals("Факультет не найден", ex.getMessage());
    }

    @Test
    void delete_ExistingFaculty_DeletedFaculty() {
        Faculty faculty = new Faculty(1L, "Hufflepuff", "Yellow");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Faculty deletedFaculty = facultyService.delete(1L);

        assertEquals(1L, deletedFaculty.getId());
    }

    @Test
    void readAll_ExistingColor_ReturnMatchingFaculties() {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(1L, "Gryffindor", "Red"));
        faculties.add(new Faculty(2L, "Ravenclaw", "Blue"));
        faculties.add(new Faculty(3L, "Hufflepuff", "Yellow"));

        when(facultyRepository.findByColor("Red")).thenReturn(faculties);

        List<Faculty> redFaculties = facultyService.readAll("Red");

        assertEquals(3, redFaculties.size());
        assertEquals("Red", redFaculties.get(0).getColor());
    }

    @Test
    void readAll_NonExistingColor_ReturnEmptyList() {
        when(facultyRepository.findByColor("Green")).thenReturn(new ArrayList<>());

        List<Faculty> greenFaculties = facultyService.readAll("Green");

        assertNotNull(greenFaculties);
        assertEquals(0, greenFaculties.size());
    }

    @Test
    public void testFindNameOrColorIgnoreCase() {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(1L, "Science", "Blue"));
        faculties.add(new Faculty(2L, "Arts", "Red"));
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("Science", "Blue")).thenReturn(faculties);

        List<Faculty> result = facultyService.findNameOrColorIgnoreCase("Science", "Blue");

        assertEquals(2, result.size());
        assertEquals("Science", result.get(0).getName());
        assertEquals("Blue", result.get(0).getColor());
        assertEquals("Arts", result.get(1).getName());
        assertEquals("Red", result.get(1).getColor());
    }

    @Test
    public void testGetListStudentsByFaculty() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John", 20, null));
        students.add(new Student(2L, "Alice", 22, null));
        when(facultyRepository.getStudentsByFaculty(1L)).thenReturn(students);

        List<Student> result = facultyService.getListStudentsByFaculty(1L);

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals(20, result.get(0).getAge());
        assertEquals("Alice", result.get(1).getName());
        assertEquals(22, result.get(1).getAge());
    }

}