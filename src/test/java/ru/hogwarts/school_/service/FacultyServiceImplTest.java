package ru.hogwarts.school_.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school_.exception.FacultyException;
import ru.hogwarts.school_.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {
    FacultyServiceImpl underTest = new FacultyServiceImpl();


    @Test
    void create_createFaculty_returnedFaculty() {
        Faculty faculty = new Faculty(null, "Slytherin", "Green");
        Faculty result = underTest.create(faculty);
        assertEquals(result, faculty);
    }


    @Test
    void create_faculty_thrownException() {
        Faculty faculty1 = new Faculty(1L, "Slytherin", "Green");
        underTest.create(faculty1);
        Faculty faculty2 = new Faculty(1L, "Slytherin", "Green");
        FacultyException ex = assertThrows(FacultyException.class, () -> underTest.create(faculty2));
        assertEquals("Такой факультет уже создан!", ex.getMessage());
    }

    @Test
    void read_existingFaculty_returnedFaculty() {
        Faculty faculty = new Faculty(null, "Gryffindor", "Red");
        underTest.create(faculty);

        Faculty result = underTest.read(faculty.getId());


        assertEquals(faculty.getId(), result.getId());
        assertEquals("Gryffindor", result.getName());
        assertEquals("Red", result.getColor());
    }

    @Test
    void read_nonExistingFaculty_thrownException() {

        FacultyException ex = assertThrows(FacultyException.class, () -> underTest.read(1L));
        assertEquals("Факультет не найден", ex.getMessage());
    }

    @Test
    void update_existingFaculty_updatedFaculty() {

        Faculty faculty = new Faculty(null, "Ravenclaw", "Blue");
        underTest.create(faculty);


        faculty.setName("NewName");
        faculty.setColor("NewColor");
        Faculty updatedFaculty = underTest.update(faculty);

        assertEquals(faculty.getId(), updatedFaculty.getId());
        assertEquals("NewName", updatedFaculty.getName());
        assertEquals("NewColor", updatedFaculty.getColor());
    }

    @Test
    void update_nonExistingFaculty_thrownException() {

        Faculty faculty = new Faculty(1L, "Hufflepuff", "Yellow");
        FacultyException ex = assertThrows(FacultyException.class, () -> underTest.update(faculty));
        assertEquals("Факультет не найден", ex.getMessage());
    }

    @Test
    void delete_existingFaculty_deletedFaculty() {
        Faculty faculty = new Faculty(null, "Hufflepuff", "Yellow");
        underTest.create(faculty);

        Faculty deletedFaculty = underTest.delete(faculty.getId());
        assertEquals(faculty.getId(), deletedFaculty.getId());
    }

    @Test
    void readAll_existingColor_returnMatchingFaculties() {
        underTest.create(new Faculty(null, "Gryffindor", "Red"));
        underTest.create(new Faculty(null, "Ravenclaw", "Blue"));
        underTest.create(new Faculty(null, "Hufflepuff", "Yellow"));


        List<Faculty> redFaculties = underTest.readAll("Red");

        assertNotNull(redFaculties);
        assertEquals(1, redFaculties.size());
        assertEquals("Red", redFaculties.get(0).getColor());
    }

    @Test
    void readAll_nonExistingColor_returnEmptyList() {
        underTest.create(new Faculty(null, "Gryffindor", "Red"));
        underTest.create(new Faculty(null, "Ravenclaw", "Blue"));
        underTest.create(new Faculty(null, "Hufflepuff", "Yellow"));

        List<Faculty> greenFaculties = underTest.readAll("Green");

        assertNotNull(greenFaculties);
        assertEquals(0, greenFaculties.size());
    }
}