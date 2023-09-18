package ru.hogwarts.school_.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.FacultyRepository;
import ru.hogwarts.school_.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;
    Student student = new Student(1L, "Germiona", 10, null);
    Faculty faculty = new Faculty(1L, "Slytherin", "green");

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
    }

    @Test
    void create__returnStatus200AndStudent() {
        Student student = new Student(1L, "Ron", 15, null);
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student",
                student,
                Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void read_studentNotInDb_returnStatus404AndStudent() {
        ResponseEntity<String> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" +
                        student.getId(), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, studentResponseEntity.getStatusCode());
        assertEquals("Студент не найден", studentResponseEntity.getBody());
    }

    @Test
    void update__returnStatus200AndStudent() {
        studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void delete__returnStatus200AndStudent() {
        studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.DELETE, new HttpEntity<>(student), Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }


    @Test
    void readAll__returnStatus200AndStudentList() {
        studentRepository.save(student);
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/" + student.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(student), exchange.getBody());
    }

    @Test
    void readFacultyByStudentId__returnStatus200AndFaculty() {
        student.setFaculty(faculty);
        facultyRepository.save(faculty);
        studentRepository.save(student);


        ResponseEntity<Faculty> exchange = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/faculty/" + student.getFaculty(),
                Faculty.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(student.getFaculty(), exchange.getBody());
    }

    @Test
    void readAllByAgeBetween__returnStatus200AndStudentList() {
        studentRepository.save(student);
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student//findByAge?min=100&max=10", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(student), exchange.getBody());

    }
}
