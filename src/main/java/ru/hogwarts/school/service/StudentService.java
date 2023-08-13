package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentService {

    Student createStudent(Student student);

    Optional<Student> getStudent(Long id);
    Collection<Student> findByAgeBetween(int fromAge, int toAge);

    Collection<Student> getAll();

    Collection<Student> getStudentsByAge(int age);

    Faculty getFacultyByStudentId(Long id);

    Student editStudent(Student student);

    Optional<Student> deleteStudent(Long id);

    void clear();
}
