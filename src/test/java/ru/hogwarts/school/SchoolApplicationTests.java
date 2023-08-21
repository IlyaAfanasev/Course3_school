package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private StudentController studentController;

	@Autowired

	private FacultyService facultyService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private StudentRepository studentRepository;

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(studentController);
	}


	@Test
	public void shouldCorrectResultFromMethodCreateStudent() {
		Faculty faculty = facultyService.getFaculty(1L).get();
		Student hermione = new Student(3L, "Hermione", 11, faculty);
		assertEquals(this.restTemplate.postForObject("http://localhost:" + port + "/student", hermione, Student.class )
				, hermione);


	}

	@Test

	public void shouldCorrectResultFromMethodGetStudentById() {
		assertNotNull(this.restTemplate.getForEntity("http://localhost:" + port + "/student/15", Student.class ));


	}

	@Test

	public void shouldThrowStudentNotFoundExceptionFromMethodGetStudentById() {
		assertThrows(StudentNotFoundException.class,
				()->this.restTemplate.getForEntity("http://localhost:" + port + "/student/20", StudentNotFoundException.class));
	}






}
