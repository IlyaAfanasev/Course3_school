package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constant.FacultyServiceConstants.*;

public class FacultyServiceImplTest {

    private FacultyRepository facultyRepositoryMock;
    private FacultyServiceImpl out;

    public FacultyServiceImplTest() {
        facultyRepositoryMock = mock(FacultyRepository.class);
        out = new FacultyServiceImpl(facultyRepositoryMock);
    }

    @Test

    public void shouldCorrectResultFromMethodCreateFaculty() {
        when(facultyRepositoryMock.save(FACULTY_1)).thenReturn(FACULTY_1);
        assertEquals(FACULTY_1, out.createFaculty(FACULTY_1));
    }

    @Test
    public void shouldCorrectResultFromMethodGetFaculty() {
        when(facultyRepositoryMock.existsById(anyLong())).thenReturn(true);
        when(facultyRepositoryMock.findById(anyLong())).thenReturn(Optional.of(FACULTY_2));
        assertEquals(Optional.of(FACULTY_2), out.getFaculty(2L));
    }

    @Test
    public void shouldThrowFacultyNotFoundFromMethodGetFaculty() {
        when(facultyRepositoryMock.existsById(anyLong())).thenReturn(false);
        assertThrows(FacultyNotFoundException.class, () -> out.getFaculty(3L));
    }

    @Test
    public void shouldCorrectResultFromMethodDeleteFaculty() {
        when(facultyRepositoryMock.existsById(anyLong())).thenReturn(true);
        when(facultyRepositoryMock.findById(anyLong())).thenReturn(Optional.of(FACULTY_3));

        assertEquals(Optional.of(FACULTY_3), out.deleteFaculty(3L));
    }

    @Test
    public void shouldThrowFacultyNotFoundFromMethodDeleteFaculty() {
        when(facultyRepositoryMock.existsById(anyLong())).thenReturn(false);
        assertThrows(FacultyNotFoundException.class, () -> out.deleteFaculty(3L));
    }

    @Test
    public void shouldCorrectResultFromMethodEditFaculty() {
        when(facultyRepositoryMock.existsById(anyLong())).thenReturn(true);

        Faculty expected = new Faculty(2L, "Faculty4", "blue");

        when(facultyRepositoryMock.save(expected)).thenReturn(expected);

        assertEquals(expected, out.editFaculty(expected));
    }

    @Test
    public void shouldThrowFacultyNotFoundFromMethodEditFaculty() {
        when(facultyRepositoryMock.existsById(anyLong())).thenReturn(false);
        assertThrows(FacultyNotFoundException.class, () -> out.editFaculty(FACULTY_2));
    }

    @Test

    public void shouldReturnCorrectResultFromMethodGetALLFaculties() {
        when(facultyRepositoryMock.findAll()).thenReturn((List<Faculty>) COLLECTIONS_FACULTIES);
        assertEquals(COLLECTIONS_FACULTIES, out.getAll());
    }

    @Test

    public void shouldReturnCorrectResultFromMethodGetFacultiesByColor() {
        when(facultyRepositoryMock.findAll()).thenReturn((List<Faculty>) COLLECTIONS_FACULTIES);
        assertEquals(COLLECTIONS_FACULTIES_BY_RED_COLOR, out.getFacultiesByColor("red"));
    }
}
