package ru.hogwarts.school_.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("/{id}")
    public Faculty read(@PathVariable long id) {
        return facultyService.read(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty delete(@PathVariable long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/faculties")
    public List<Faculty> findFacultyIgnoreCase(@RequestParam String name, @RequestParam String color) {
        return facultyService.findNameOrColorIgnoreCase(name, color);
    }

    @GetMapping("/students/{id}")
    public List<Student> getFacultyStudents(@PathVariable long id) {
        return facultyService.getListStudentsByFaculty(id);
    }

    @GetMapping("/color/{color}")
    public List<Faculty> findColor(@PathVariable String color) {
        return facultyService.findByColor(color);
    }
}


