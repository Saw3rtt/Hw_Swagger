package ru.hogwarts.school_.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.StudentRepository;
import ru.hogwarts.school_.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    public final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
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
    public List<Student> readAll(@PathVariable int age) {
        return studentService.readAll(age);
    }

    @GetMapping("/findByAge")
    public List<Student> findStudentByAge(@RequestParam("max") int max, @RequestParam("min") int min) {
        return studentRepository.findByAgeBetween(min, max);
    }
}
