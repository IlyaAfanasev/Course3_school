package ru.hogwarts.school.constant;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StudentServiceConstants {

    public static final Student STUDENT_1 = new Student(1L, "Student1", 10);
    public static final Student STUDENT_2 = new Student(2L, "Student2", 11);
    public static final Student STUDENT_3 = new Student(3L, "Student3", 10);

    public static final Collection<Student> COLLECTIONS_STUDENTS_BY_AGE_10
            = new ArrayList<>(List.of(STUDENT_1,STUDENT_3));

    public static final Collection<Student> COLLECTIONS_STUDENTS
            = Collections.unmodifiableList(new ArrayList<>(List.of(STUDENT_1, STUDENT_2, STUDENT_3)));
}
