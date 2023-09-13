package ru.hogwarts.school_.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school_.exception.StudentException;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.StudentRepository;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("Такой студент уже существует!");
        }

        return studentRepository.save(student);
    }

    @Override
    public Student read(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("Студент не найден");
        }
        return student.get();
    }

    @Override
    public Student update(Student student) {
        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("Студент не найден");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student delete(long id) {
        Optional<Student> student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return student.get();
    }

    @Override
    public List<Student> readAll(int age) {
        return studentRepository.findByAge(age);
    }
}
