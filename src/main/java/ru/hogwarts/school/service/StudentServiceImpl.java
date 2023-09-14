package ru.hogwarts.school.service;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import static org.apache.commons.lang3.StringUtils.*;

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

        List<Student> studentList = new ArrayList<>(studentRepository.findAll());

        for (int i = 0; i < 2; i++) {
            printStudent(studentList.get(i));
        }
        new Thread(()->{
            for (int i = 2; i <4 ; i++) {
                printStudent(studentList.get(i));

            }
        }).start();
        new Thread(()->{
            for (int i = 4; i <6 ; i++) {
                printStudent(studentList.get(i));

            }
        }).start();

        return studentRepository.getNumberOfStudents();
    }
    private void printStudent(Student student) {

        synchronized (student) {

            System.out.println(student.getName()+" "+student.getId());

        }
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
        Collection<Student> studentCollection = studentRepository.findAll();

        List<Student> studentList = new ArrayList<>(studentCollection);
        for (int i = 0; i < 2; i++) {

        }
        new Thread(()->{
            for (int i = 2; i < 4; i++) {

                System.out.println(studentList.get(i).getName()+" "+studentList.get(i).getId());
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } ).start();
        new Thread(()->{
            for (int i = 4; i < 6; i++) {

                System.out.println(studentList.get(i).getName()+" "+studentList.get(i).getId());
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } ).start();


        return studentCollection;
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

    @Override
    public Collection<String> getSortedStudentsWithNameStarts_A() {

        return studentRepository.findAll()
                .stream()
                .parallel()
                .filter(s -> s.getName().startsWith("A"))
                .map(Student::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public String getAverageAgeOfStudentsByFindAll() {
        int avgAge =(int)studentRepository.findAll()
                    .stream().mapToInt(Student::getAge).average().getAsDouble();
        return "Средний возраст студентов "+avgAge;
    }
}
