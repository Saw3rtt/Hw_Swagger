package ru.hogwarts.school_.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);

    }
    @GetMapping("/age/{age}")
    public List<Student> readAll(@PathVariable int age){
        return studentService.readAll(age);
    }
}
