package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
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
            throw new StudentNotFoundException("Student not found");
        }
        return studentRepository.findById(id);
    }


    public Collection<Student> getAll() {

        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(int age) {

        return studentRepository.findAll().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int fromAge, int toAge) {

        if (studentRepository.findByAgeBetween(fromAge, toAge).isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        return studentRepository.findByAgeBetween(fromAge, toAge);
    }

    public Faculty getFacultyByStudentId(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found");
        }
       return studentRepository.findById(id).get().getFaculty();
    }

    public Student editStudent(Student student) {

        if (!studentRepository.existsById(student.getId())) {
            throw new StudentNotFoundException("Student not found");
        }

        return studentRepository.save(student);

    }

    public Optional<Student> deleteStudent(Long id) {
        Optional<Student> student;
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found");
        }
        student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return student;
    }


    public void clear() {
        studentRepository.deleteAll();
    }
}
