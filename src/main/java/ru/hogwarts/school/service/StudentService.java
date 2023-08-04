package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student editStudent(Student student);

    Student deleteStudent(Long id);

    Collection<Student> getAll();

    Collection<Student> getStudentsByAge(int age);

    void clear();
}
