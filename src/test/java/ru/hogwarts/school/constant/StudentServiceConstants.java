package ru.hogwarts.school.constant;

import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.hogwarts.school.constant.FacultyServiceConstants.*;

public class StudentServiceConstants {

    public static final Student STUDENT_1 = new Student(1L, "Student1", 10, FACULTY_1);
    public static final Student STUDENT_2 = new Student(2L, "Student2", 12, FACULTY_2);
    public static final Student STUDENT_3 = new Student(3L, "Student3", 10, FACULTY_3);
    public static final Student STUDENT_4 = new Student(1L, "Student4", 13, FACULTY_1);

    public static final Collection<Student> COLLECTIONS_STUDENTS_BY_AGE_10
            = new ArrayList<>(List.of(STUDENT_1,STUDENT_3));

    public static final Collection<Student> COLLECTIONS_STUDENTS
            = Collections.unmodifiableList(new ArrayList<>(List.of(STUDENT_1, STUDENT_2, STUDENT_3)));
    public static final Collection<Student> COLLECTIONS_STUDENTS_ON_FACULTY1
            = Collections.unmodifiableList(new ArrayList<>(List.of(STUDENT_1, STUDENT_4)));
}
