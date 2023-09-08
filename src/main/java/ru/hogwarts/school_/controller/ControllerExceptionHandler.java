package ru.hogwarts.school_.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school_.exception.StudentException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(StudentException.class)
    public ResponseEntity<String> handleStudentException(StudentException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
