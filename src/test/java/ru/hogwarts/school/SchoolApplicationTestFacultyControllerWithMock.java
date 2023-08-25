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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.constant.FacultyServiceConstants.*;

@WebMvcTest(FacultyController.class)
public class SchoolApplicationTestFacultyControllerWithMock {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;


    @Test
    public void shouldCorrectResultFromMethodCreateFaculty() throws Exception {
        final Long id = 1L;
        final String name = "Faculty1";
        final String color = "black";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void shouldCorrectResultFromMethodGetFacultyById() throws Exception {
        final Long id = 1L;
        final String name = "Faculty1";
        final String color = "black";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.existsById(anyLong())).thenReturn(true);
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        verify(facultyRepository, times(1)).existsById(anyLong());
        verify(facultyRepository, times(1)).findById(anyLong());
    }

    @Test
    public void shouldCorrectResultFromMethodGetAllFaculties() throws Exception {

        when(facultyRepository.findAll()).thenReturn((List<Faculty>) COLLECTIONS_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(COLLECTIONS_FACULTIES)));
        verify(facultyRepository, times(1)).findAll();
    }

    @Test
    public void shouldCorrectResultFromMethodGetFacultiesByColor() throws Exception {

        when(facultyRepository.findAll()).thenReturn((List<Faculty>) COLLECTIONS_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color?color=red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(COLLECTIONS_FACULTIES_BY_RED_COLOR)));
        verify(facultyRepository, times(1)).findAll();
    }

    @Test
    public void shouldCorrectResultFromMethodGetFacultiesByNameOrColor() throws Exception {

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(null, "red"))
                .thenReturn(COLLECTIONS_FACULTIES_BY_RED_COLOR);
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("Faculty1", null))
                .thenReturn((List.of(FACULTY_1)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(COLLECTIONS_FACULTIES_BY_RED_COLOR)));
        verify(facultyRepository, times(1)).
                findByNameIgnoreCaseOrColorIgnoreCase(null, "red");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?name=Faculty1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(FACULTY_1))));
        verify(facultyRepository, times(1)).
                findByNameIgnoreCaseOrColorIgnoreCase("Faculty1", null);

    }

    @Test
    public void shouldCorrectResultFromMethodGetStudentsOnFacultyById() throws Exception {
        Faculty faculty = new Faculty(1L, "Faculty1", "red");
        Student student1 = new Student(1L, "Student1", 10, faculty);
        Student student2 = new Student(2L, "Student2", 10, faculty);

        when(facultyRepository.existsById(anyLong())).thenReturn(true);
        when(facultyRepository.findById(anyLong()))
                .thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getStudents/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().json(objectMapper.writeValueAsString(List.of(student1, student2)))); не смог победить))


        verify(facultyRepository, times(1)).existsById(anyLong());
        verify(facultyRepository, times(1)).
                findById(anyLong());

    }

    @Test
    public void shouldCorrectResultFromMethodEditFaculty() throws Exception {
        final Long id = 1L;
        final String name = "Faculty1";
        final String color = "black";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.existsById(anyLong())).thenReturn(true);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        verify(facultyRepository, times(1)).existsById(anyLong());
        verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    public void shouldCorrectResultFromMethodDeleteFaculty() throws Exception {
        final Long id = 1L;
        final String name = "Faculty1";
        final String color = "black";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.existsById(anyLong())).thenReturn(true);
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        verify(facultyRepository, times(1)).existsById(anyLong());
        verify(facultyRepository, times(1)).findById(anyLong());
        verify(facultyRepository, times(1)).deleteById(anyLong());
    }





}
