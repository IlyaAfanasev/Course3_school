package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constant.FacultyServiceConstants.*;
import static ru.hogwarts.school.constant.StudentServiceConstants.*;

public class StudentServiceImplTest {

    private StudentRepository studentRepositoryMock;
    private StudentServiceImpl out;

    @BeforeEach
    public void init() {
        studentRepositoryMock = mock(StudentRepository.class);
        out = new StudentServiceImpl(studentRepositoryMock);
    }

    @Test

    public void shouldCorrectResultFromMethodCreateStudent() {
        when(studentRepositoryMock.save(STUDENT_1)).thenReturn(STUDENT_1);

        assertEquals(STUDENT_1, out.createStudent(STUDENT_1));
        verify(studentRepositoryMock, times(1)).save(STUDENT_1);
    }

    @Test
    public void shouldCorrectResultFromMethodGetStudent() {
        when(studentRepositoryMock.existsById(anyLong())).thenReturn(true);

        when(studentRepositoryMock.findById(anyLong())).thenReturn(Optional.of(STUDENT_2));

        assertEquals(Optional.of(STUDENT_2), out.getStudent(2L));

        verify(studentRepositoryMock, times(1)).existsById(anyLong());
        verify(studentRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void shouldThrowStudentNotFoundFromMethodGetStudent() {
        when(studentRepositoryMock.existsById(anyLong())).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> out.getStudent(2L));

        verify(studentRepositoryMock, times(1)).existsById(anyLong());


    }

    @Test
    public void shouldReturnCorrectResultFromMethodGetFacultyByStudentId() {
        when(studentRepositoryMock.existsById(anyLong())).thenReturn(true);

        when(studentRepositoryMock.findById(anyLong())).thenReturn(Optional.of(STUDENT_2));
        assertEquals(FACULTY_2, out.getFacultyByStudentId(2L));

        verify(studentRepositoryMock, times(1)).existsById(anyLong());
        verify(studentRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void shouldThrowStudentNotFoundFromMethodGetFacultyByStudentId() {
        when(studentRepositoryMock.existsById(anyLong())).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> out.getFacultyByStudentId(2L));

        verify(studentRepositoryMock, times(1)).existsById(anyLong());

    }


    @Test
    public void shouldCorrectResultFromMethodEditStudent() {
        when(studentRepositoryMock.existsById(anyLong())).thenReturn(true);

        Student expected = new Student(2L, "Student4", 12, FACULTY_1);

        when(studentRepositoryMock.save(expected)).thenReturn(expected);

        assertEquals(expected, out.editStudent(expected));

        verify(studentRepositoryMock, times(1)).existsById(anyLong());
        verify(studentRepositoryMock, times(1)).save(expected);
    }

    @Test
    public void shouldThrowStudentNotFoundFromMethodEditStudent() {
        when(studentRepositoryMock.existsById(anyLong())).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> out.editStudent(STUDENT_1));
        verify(studentRepositoryMock, times(1)).existsById(anyLong());

    }


    @Test
    public void shouldCorrectResultFromMethodDeleteStudent() {
        when(studentRepositoryMock.existsById(anyLong())).thenReturn(true);
        when(studentRepositoryMock.findById(anyLong())).thenReturn(Optional.of(STUDENT_3));

        assertEquals(Optional.of(STUDENT_3), out.deleteStudent(3L));

        verify(studentRepositoryMock, times(1)).existsById(anyLong());
        verify(studentRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void shouldThrowStudentNotFoundFromMethodDeleteStudent() {
        when(studentRepositoryMock.existsById(anyLong())).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> out.deleteStudent(3L));

        verify(studentRepositoryMock, times(1)).existsById(anyLong());

    }

    @Test

    public void shouldReturnCorrectResultFromMethodGetALLStudents() {
        when(studentRepositoryMock.findAll()).thenReturn((List<Student>) COLLECTIONS_STUDENTS);
        assertEquals(COLLECTIONS_STUDENTS, out.getAll());

        verify(studentRepositoryMock, times(1)).findAll();
    }

    @Test

    public void shouldReturnCorrectResultFromMethodGetStudentsByAge() {
        when(studentRepositoryMock.findAll()).thenReturn((List<Student>) COLLECTIONS_STUDENTS);

        assertEquals(COLLECTIONS_STUDENTS_BY_AGE_10, out.getStudentsByAge(10));

        verify(studentRepositoryMock, times(1)).findAll();
    }


}
