package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {

        return studentRepository.save(student);

    }

    public Optional<Student> getStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException();
        }
        return studentRepository.findById(id);
    }

    public Student editStudent(Student student) {

        if (!studentRepository.existsById(student.getId())) {
            throw new StudentNotFoundException();
        }

        return studentRepository.save(student);

    }

    public Student deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException();
        }
        return null;
    }

    public Collection<Student> getAll() {

        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(int age) {

        return studentRepository.findAll().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        studentRepository.deleteAll();
    }
}
