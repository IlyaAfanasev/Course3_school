package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constant.FacultyServiceConstants.*;

public class FacultyServiceImplTest {

    private final FacultyServiceImpl out = new FacultyServiceImpl();

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
