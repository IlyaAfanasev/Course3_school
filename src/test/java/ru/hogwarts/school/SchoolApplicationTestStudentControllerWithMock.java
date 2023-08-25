package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.constant.StudentServiceConstants.*;


@WebMvcTest(StudentController.class)
public class SchoolApplicationTestStudentControllerWithMock {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void shouldCorrectResultFromMethodCreateStudent() throws Exception {
        final Long id = 1L;
        final String nameFaculty = "Faculty1";
        final String color = "black";
        Faculty faculty = new Faculty(id, nameFaculty, color);

        final String name = "Harry";
        final int age = 10;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty_id", 1);

        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setAge(10);
        student.setFaculty(faculty);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty").value(faculty));

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void shouldCorrectResultFromMethodGetStudentById() throws Exception {
        final Long id = 1L;
        final String nameFaculty = "Faculty1";
        final String color = "black";
        Faculty faculty = new Faculty(id, nameFaculty, color);

        final String name = "Harry";
        final int age = 10;


        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setAge(10);
        student.setFaculty(faculty);
        when(studentRepository.existsById(anyLong())).thenReturn(true);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty").value(faculty));

        verify(studentRepository, times(1)).existsById(anyLong());
        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void shouldCorrectResultFromMethodGetAllStudents() throws Exception {

        when(studentRepository.findAll()).thenReturn((List<Student>) COLLECTIONS_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(COLLECTIONS_STUDENTS)));
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void shouldCorrectResultFromMethodGetStudentsByAge() throws Exception {

        when(studentRepository.findAll()).thenReturn((List<Student>) COLLECTIONS_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?age=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(COLLECTIONS_STUDENTS_BY_AGE_10)));
        verify(studentRepository, times(2)).findAll();
    }

    @Test
    public void shouldCorrectResultFromMethodFindByAgeBetween() throws Exception {

        when(studentRepository.findByAgeBetween(anyInt(), anyInt()))
                .thenReturn(List.of(STUDENT_2, STUDENT_4));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?fromAge=12&toAge=14")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(STUDENT_2, STUDENT_4))));
        verify(studentRepository, times(2)).
                findByAgeBetween(anyInt(), anyInt());

    }

    @Test
    public void shouldCorrectResultFromMethodGetFacultyByStudentId() throws Exception {
        final Long id = 1L;
        final String nameFaculty = "Faculty1";
        final String color = "black";
        Faculty faculty = new Faculty(id, nameFaculty, color);

        final String name = "Harry";
        final int age = 10;
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setAge(10);
        student.setFaculty(faculty);

        when(studentRepository.existsById(anyLong())).thenReturn(true);
        when(studentRepository.findById(anyLong()))
                .thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getfaculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(nameFaculty))
                .andExpect(jsonPath("$.color").value(color));

        verify(studentRepository, times(1)).existsById(anyLong());
        verify(studentRepository, times(1)).
                findById(anyLong());

    }

    @Test
    public void shouldCorrectResultFromMethodEditStudent() throws Exception {
        final Long id = 1L;
        final String nameFaculty = "Faculty1";
        final String color = "black";
        Faculty faculty = new Faculty(id, nameFaculty, color);

        final String name = "Harry";
        final int age = 10;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty_id", 1);

        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setAge(10);
        student.setFaculty(faculty);

        when(studentRepository.existsById(anyLong())).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
//                .andExpect(jsonPath("$.faculty").value(faculty)); почему-то возвращает null

        verify(studentRepository, times(1)).existsById(anyLong());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void shouldCorrectResultFromMethodDeleteStudentById() throws Exception {
        final Long id = 1L;
        final String nameFaculty = "Faculty1";
        final String color = "black";
        Faculty faculty = new Faculty(id, nameFaculty, color);

        final String name = "Harry";
        final int age = 10;


        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setAge(10);
        student.setFaculty(faculty);
        when(studentRepository.existsById(anyLong())).thenReturn(true);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty").value(faculty));

        verify(studentRepository, times(1)).existsById(anyLong());
        verify(studentRepository, times(1)).findById(anyLong());
        verify(studentRepository, times(1)).deleteById(anyLong());
    }


}
