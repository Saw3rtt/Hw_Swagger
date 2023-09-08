package ru.hogwarts.school_.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school_.exception.StudentException;
import ru.hogwarts.school_.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();

    private long counter;

    @Override
    public Student create(Student student) {
        if (studentMap.containsValue(student)) {
            throw new StudentException("Такой студент уже существует!");
        }
        student.setId(++counter);
        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student read(long id) {
        if (!studentMap.containsKey(id)) {
            throw new StudentException("Студент не найден");
        }
        return studentMap.get(id);
    }

    @Override
    public Student update(Student student) {
        if (!studentMap.containsKey(student.getId())) {
            throw new StudentException("Студент не найден");
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student delete(long id) {
        return studentMap.remove(id);
    }
    @Override
    public List<Student> readAll(int age){
        return studentMap.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
