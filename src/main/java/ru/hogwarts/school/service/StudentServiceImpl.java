package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {

        logger.info("Was invoked method for create student");
        return studentRepository.save(student);

    }

    @Override
    public int getNumberOfStudents() {
        logger.info("Was invoked method for get number of students ");
        return studentRepository.getNumberOfStudents();
    }

    @Override
    public int getAverageAgeOfStudents() {
        logger.info("Was invoked method for get average age of students ");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public List<Student> getLast_5_Student() {
        logger.info("Was invoked method for get the last 5 students ");
        return studentRepository.getLast_5_Student();
    }

    public Optional<Student> getStudent(Long id) {
        logger.info("Was invoked method for get student by Id ");
        if (!studentRepository.existsById(id)) {
            logger.error("There is not student with id = " + id);
            throw new StudentNotFoundException("Student not found");
        }
        return studentRepository.findById(id);
    }


    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students ");

        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method for get students by age ");

        return studentRepository.findAll().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int fromAge, int toAge) {
        logger.info("Was invoked method for get student by age between ");

        if (studentRepository.findByAgeBetween(fromAge, toAge).isEmpty()) {
            logger.error("There are not students");
            throw new StudentNotFoundException("Student not found");
        }
        return studentRepository.findByAgeBetween(fromAge, toAge);
    }

    public Faculty getFacultyByStudentId(Long id) {
        logger.info("Was invoked method for get faculty by student_id ");
        if (!studentRepository.existsById(id)) {
            logger.error("There is not student with id = " + id);
            throw new StudentNotFoundException("Student not found");
        }
       return studentRepository.findById(id).get().getFaculty();
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student ");

        if (!studentRepository.existsById(student.getId())) {
            logger.error("There is not student with id = " + student.getId());
            throw new StudentNotFoundException("Student not found");
        }

        return studentRepository.save(student);

    }

    public Optional<Student> deleteStudent(Long id) {
        logger.info("Was invoked method for delete student ");
        Optional<Student> student;
        if (!studentRepository.existsById(id)) {
            logger.error("There is not student with id = " + id);
            throw new StudentNotFoundException("Student not found");
        }
        student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return student;
    }


    public void clear() {
        logger.info("Was invoked method for clear table student");
        studentRepository.deleteAll();
    }
}
