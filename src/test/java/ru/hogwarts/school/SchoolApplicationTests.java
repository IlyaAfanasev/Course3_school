package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private StudentController studentController;

	@Autowired
	private StudentService studentService;

	@Autowired

	private FacultyService facultyService;

	@Autowired
	private FacultyRepository facultyRepository;

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
		Student hermione = new Student();
		hermione.setId(3L);
		hermione.setName("Hermione");
		hermione.setAge(11);
		hermione.setFaculty(faculty);
		HttpEntity<Student> expected = new HttpEntity<>(hermione);

		assertEquals(this.restTemplate.postForObject("http://localhost:" + port + "/student", hermione, Student.class )
				, hermione);

	}

	@Test
	public void shouldCorrectResultFromMethodGetStudentById() {
		Faculty faculty = facultyService.getFaculty(1L).get();
		Student hermione = new Student(15L, "Hermione", 11, faculty);
		//		assertEquals(this.restTemplate.getForEntity("http://localhost:" + port + "/student/15", Student.class ), hermione);

		ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port + "/student/15", Student.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getName(), "Hermione");


	}

	@Test
	public void shouldThrowStudentNotFoundExceptionFromMethodGetStudentById() {
		ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port + "/student/150", Student.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}


	@Test
	public void shouldCorrectResultFromMethodGetAll() {
		assertNotNull(this.restTemplate.getForEntity("http://localhost:" + port + "/student", String.class));
	}

	@Test

	public void shouldCorrectResultFromMethodGetAllByAgeBetween() {
		assertNotNull(this.restTemplate.getForEntity("http://localhost:" + port + "/student?fromAge=10&toAge=15", String.class));
	}

	@Test
	public void shouldReturnStatusNotFoundFromMethodGetAllByAgeBetween() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/student?fromAge=2&toAge=5",
				String.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}


	@Test
	public void shouldCorrectResultFromMethodGetStudentsByAge() {
		ResponseEntity<String> response =this.restTemplate.getForEntity("http://localhost:" + port + "/student/age?age=10",
				String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}
	@Test
	public void shouldReturnStatusBadRequestFromMethodGetStudentsByAge() {
		ResponseEntity<String> response =this.restTemplate.getForEntity("http://localhost:" + port + "/student/age?age=-1",
				String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	public void shouldThrowStudentNotFoundExceptionFromMethodGetStudentByAge() {
		ResponseEntity<String> response =this.restTemplate.getForEntity("http://localhost:" + port + "/student/age?age=50", String.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}



	@Test
	public void shouldCorrectResultFromMethodEditStudent() {
		Faculty faculty = facultyService.getFaculty(1L).get();
		Student hermione = new Student(20L, "Hermione", 11, faculty);
		Long id = studentRepository.save(hermione).getId();
		Faculty faculty1 = facultyService.getFaculty(3L).get();
		Student gilderoy = new Student(id, "Gilderoy", 27, faculty1);
		HttpEntity<Student> entity = new HttpEntity<>(gilderoy);
		ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT
				, entity, Student.class);

		assertEquals(response.getBody(), gilderoy);
	}
	@Test
	public void shouldReturnStatusNotFoundFromMethodEditStudent() {
		Faculty faculty = facultyService.getFaculty(3L).get();
		Student gilderoy = new Student(150L, "Gilderoy", 27, faculty);
		HttpEntity<Student> entity = new HttpEntity<>(gilderoy);
		ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT
				, entity, Student.class);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void shouldCorrectResultFromMethodDeleteStudent() {
		Faculty faculty = facultyService.getFaculty(1L).get();
		Student hermione = new Student(20L, "Hermione", 11, faculty);
		Long id = studentRepository.save(hermione).getId();
		HttpEntity<Student> entity = new HttpEntity<>(hermione);
		ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port + "/student/"+id, HttpMethod.DELETE
				, null, Student.class);

		assertEquals(response.getBody(), hermione);
	}
	@Test
	public void shouldReturnStatusNotFoundFromMethodDeleteStudent() {

		ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port + "/student/150", HttpMethod.DELETE
				, null, Student.class);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

	}



}
