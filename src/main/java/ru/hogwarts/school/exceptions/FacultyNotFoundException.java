package ru.hogwarts.school.exceptions;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException() {
    }

    public FacultyNotFoundException(String message) {
        super(message);
    }

    public FacultyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

