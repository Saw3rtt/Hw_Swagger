package ru.hogwarts.school_.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.StudentRepository;

import java.util.List;

@Service
public class ThreadService {
    private Logger logger = LoggerFactory.getLogger(ThreadService.class);
    private StudentRepository studentRepository;

    public ThreadService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void thread(){
        List<Student> all = studentRepository.findAll();
        logStudent(all.get(0));
        logStudent(all.get(1));

        new Thread(() -> {
            logStudent(all.get(2));
            logStudent(all.get(3));
        }).start();

        new Thread(() -> {
            logStudent(all.get(4));
            logStudent(all.get(5));
        }).start();

    }
    private synchronized void logStudent(Student student){
       try{
           Thread.sleep(1000);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
        logger.info(student.toString());
    }
}
