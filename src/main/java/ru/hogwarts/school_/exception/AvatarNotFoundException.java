package ru.hogwarts.school_.exception;

public class AvatarNotFoundException extends RuntimeException{
    public AvatarNotFoundException(String message) {
        super(message);
    }
}
