package ru.hogwarts.school_.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_.exception.StudentException;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.StudentRepository;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Был вызван метод create с данными" + student);
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("Такой студент уже существует!");
        }

        Student savedStudent = studentRepository.save(student);
        logger.info("Из метода create вернули" + student);
        return savedStudent;
    }

    @Override
    public Student read(long id) {
        logger.info("Был вызван метод read с данными" + id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("Студент не найден");
        }
        Student studentSaved = student.get();
        logger.info("из метода read получили " + studentSaved);
        return studentSaved;
    }

    @Override
    public Student update(Student student) {
        logger.info("был вызван метод для update с данными" + student);
        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("Студент не найден");
        }
        Student saveStudent = studentRepository.save(student);
        logger.info("из метода update вернули " + saveStudent);
        return saveStudent;
    }

    @Override
    public Student delete(long id) {
        logger.info("был вызван метод для delete с данными" + id);
        Optional<Student> student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        Student studentSaved = student.get();
        logger.info("из метода delete удалила студента " + studentSaved);
        return studentSaved;
    }

    @Override
    public List<Student> readAll(int age) {
        logger.info("был вызван метод для readAll всех студентов");
        List<Student> studentSaved = studentRepository.findAll();
        logger.info("из метода readAll вернули всех студентов" + studentSaved);
        return studentSaved;
    }

    @Override
    public List<Student> findStudentsByAge(int min, int max) {
        logger.info("был вызван метод для findStudentsByAge с данными минимум и максимум " + min + max);
        List<Student> studentsByAge = studentRepository.findByAgeBetween(min, max);
        logger.info("из метода findStudentsByAge вернули студента " + studentsByAge);
        return studentsByAge;
    }

    @Override
    public Faculty getStudentFaculty(long studentId) {
        logger.info("был вызван метод для getStudentFaculty с данными " + studentId);
        Optional<Student> faculty = studentRepository.findStudentById(studentId);
        if (faculty.isEmpty()) {
            throw new StudentException("Студент не найден!");
        }
        logger.info("из метода getStudentFaculty нашли по айди факультет студента " + studentId);
        return faculty.get().getFaculty();
    }

    @Override
    public Long findStudentCount() {
        logger.info("был вызван метод для findStudentCount");
        Long student1 = studentRepository.findStudentCount();
        logger.info("из метода findStudentCount вернули " + student1);
        return student1;
    }

    @Override
    public Integer findAverageAge() {
        logger.info("был вызван метод для findAverageAge");
        Integer averageAge = studentRepository.findAverageAge();
        logger.info("из метода findAverageAge вернули средний возвраст студента " + averageAge);
        return averageAge;
    }

    public List<Student> findFiveLastStudent() {
        logger.info("был вызван метод для findFiveLastStudents");
        List<Student> lastFiveStudent = studentRepository.findLastStudent(5);
        logger.info("из метода findFiveLastStudents нашли последних 5 студентов " + lastFiveStudent);
        return lastFiveStudent;
    }
}

