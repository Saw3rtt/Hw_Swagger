package ru.hogwarts.school_.service;


import ru.hogwarts.school_.model.Student;

public interface ThreadService {

    public void threadOne();

    public void threadTwo();

    public void studentLog(Student student);
}