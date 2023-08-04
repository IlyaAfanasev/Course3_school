package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> students;

    private Long studentId = 0L;

    public StudentServiceImpl() {
        this.students = new HashMap<>();
    }

    public Student createStudent(Student student) {
        student.setId(++studentId);
        students.put(studentId, student);

        return student;

    }

    public Student getStudent(Long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        return null;
    }

    public Student editStudent(Student student) {

        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return students.get(student.getId());
        }

        return null;

    }

    public Student deleteStudent(Long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        return null;
    }

    public Collection<Student> getAll() {

        return Collections.unmodifiableList(new ArrayList<>(students.values()));
    }

    public Collection<Student> getStudentsByAge(int age) {

        return students.values().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        students.clear();
    }
}
