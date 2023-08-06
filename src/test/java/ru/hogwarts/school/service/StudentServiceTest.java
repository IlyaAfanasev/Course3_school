package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.constant.StudentServiceConstants;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.hogwarts.school.constant.FacultyServiceConstants.*;
import static ru.hogwarts.school.constant.FacultyServiceConstants.COLLECTIONS_FACULTIES_BY_RED_COLOR;
import static ru.hogwarts.school.constant.StudentServiceConstants.*;

public class StudentServiceTest {

    private final StudentService out = new StudentService();

    @Test

    public void shouldCorrectResultFromMethodCreateStudent() {
        assertEquals(STUDENT_1, out.createStudent(STUDENT_1));
    }
    @Test
    public void shouldCorrectResultFromMethodGetStudent() {
        out.createStudent(STUDENT_1);
        out.createStudent(STUDENT_2);
        out.createStudent(STUDENT_3);

        assertEquals(STUDENT_2, out.getStudent(2L));
    }

    @Test
    public void shouldCorrectResultFromMethodEditStudent() {
        out.createStudent(STUDENT_1);
        out.createStudent(STUDENT_2);
        out.createStudent(STUDENT_3);

        Student expected = new Student(2L, "Student4", 12);

        assertEquals(expected, out.editStudent(expected));
    }


    @Test
    public void shouldCorrectResultFromMethodDeleteStudent() {
        out.createStudent(STUDENT_1);
        out.createStudent(STUDENT_2);
        out.createStudent(STUDENT_3);

        assertEquals(STUDENT_3, out.deleteStudent(3L));
    }

    @Test

    public void shouldReturnCorrectResultFromMethodGetALLStudents() {
        out.createStudent(STUDENT_1);
        out.createStudent(STUDENT_2);
        out.createStudent(STUDENT_3);
        assertEquals(COLLECTIONS_STUDENTS, out.getAll());
    }
    @Test

    public void shouldReturnCorrectResultFromMethodGetStudentsByAge() {
        out.createStudent(STUDENT_1);
        out.createStudent(STUDENT_2);
        out.createStudent(STUDENT_3);
        assertEquals(COLLECTIONS_STUDENTS_BY_AGE_10, out.getStudentsByAge(10));
    }
}
