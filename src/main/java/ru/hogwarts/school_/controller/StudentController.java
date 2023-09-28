package ru.hogwarts.school_.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.StudentRepository;
import ru.hogwarts.school_.service.StudentService;
import ru.hogwarts.school_.service.StudentServiceImpl;

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

    @PutMapping("/{id}")
    public Student update(@PathVariable long id, @RequestBody Student student) {
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
    public List<Student> findStudent(@RequestParam("max") int max, @RequestParam("min") int min) {
        return studentService.findStudentsByAge(max, min);
    }

    @GetMapping("/faculty/{id}")
    public Faculty getStudentFaculty(@PathVariable long id) {
        return studentService.getStudentFaculty(id);
    }

    @GetMapping("/count")
    public Long findStudentCount() {
        return studentService.findStudentCount();
    }

    @GetMapping("/age/avg")
    public Integer findAverageAge() {
        return studentService.findAverageAge();
    }

    @GetMapping("/five-last-students")
    public List<Student> findFiveLastStudents() {
        return studentService.findFiveLastStudent();
    }

    @GetMapping("/name-with-a")
    public List<String> findStudentNameThatStartWithA() {
        return studentService.findNameWithFirstLetterIsA();
    }

    @GetMapping("/age-avg-with-stream")
    public Double findAverageAgeByStream() {
        return studentService.findAvgAgeByStream();
    }
}

