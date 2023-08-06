package ru.hogwarts.school.service;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.constant.FacultyServiceConstants;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constant.FacultyServiceConstants.*;
import static ru.hogwarts.school.constant.StudentServiceConstants.*;

public class FacultyServiceTest {

    private final FacultyService out = new FacultyService();

    @Test

    public void shouldCorrectResultFromMethodCreateFaculty() {
        assertEquals(FACULTY_1, out.createFaculty(FACULTY_1));
    }
    @Test
    public void shouldCorrectResultFromMethodGetFaculty() {
        out.createFaculty(FACULTY_1);
        out.createFaculty(FACULTY_2);
        out.createFaculty(FACULTY_3);

        assertEquals(FACULTY_2, out.getFaculty(2L));
    }
    @Test
    public void shouldCorrectResultFromMethodDeleteFaculty() {
        out.createFaculty(FACULTY_1);
        out.createFaculty(FACULTY_2);
        out.createFaculty(FACULTY_3);

        assertEquals(FACULTY_3, out.deleteFaculty(3L));
    }

    @Test
    public void shouldCorrectResultFromMethodEditFaculty() {
        out.createFaculty(FACULTY_1);
        out.createFaculty(FACULTY_2);
        out.createFaculty(FACULTY_3);

        Faculty expected = new Faculty(2L, "Faculty4", "blue");

        assertEquals(expected, out.editFaculty(expected));
    }

    @Test

    public void shouldReturnCorrectResultFromMethodGetALLFaculties() {
        out.createFaculty(FACULTY_1);
        out.createFaculty(FACULTY_2);
        out.createFaculty(FACULTY_3);
        assertEquals(COLLECTIONS_FACULTIES, out.getAll());
    }
    @Test

    public void shouldReturnCorrectResultFromMethodGetFacultiesByColor() {
        out.createFaculty(FACULTY_1);
        out.createFaculty(FACULTY_2);
        out.createFaculty(FACULTY_3);
        assertEquals(COLLECTIONS_FACULTIES_BY_RED_COLOR, out.getFacultiesByColor("red"));
    }
}
