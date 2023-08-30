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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
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
		Faculty faculty = facultyService.getFacultyById(1L).get();
		Student hermione = new Student();
		hermione.setId(null);
		hermione.setName("Hermione");
		hermione.setAge(11);
		hermione.setFaculty(faculty);

		Student studentTest = this.restTemplate.postForObject("http://localhost:" + port + "/student", hermione, Student.class );
		try {
			assertEquals(hermione, studentTest);
		} finally {
			studentRepository.deleteById(studentTest.getId());
		}



	}

	@Test
	public void shouldCorrectResultFromMethodGetStudentById() {
		Faculty faculty = facultyService.getFacultyById(1L).get();
		Student hermione = new Student(null, "Hermione", 11, faculty);
		Long id = studentRepository.save(hermione).getId();

		ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port + "/student/"+id, Student.class);
		try {
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(hermione, response.getBody());
		}finally {
			studentRepository.deleteById(id);
		}



	}

	@Test
	public void shouldThrowStudentNotFoundExceptionFromMethodGetStudentById() {
		Long id = studentRepository.count()+1;
		ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port + "/student/"+id, Student.class);
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
		Faculty faculty = facultyService.getFacultyById(1L).get();
		Student hermione = new Student(20L, "Hermione", 11, faculty);
		Long id = studentRepository.save(hermione).getId();
		Faculty faculty1 = facultyService.getFacultyById(3L).get();
		Student gilderoy = new Student(id, "Gilderoy", 27, faculty1);
		HttpEntity<Student> entity = new HttpEntity<>(gilderoy);
		ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT
				, entity, Student.class);

		assertEquals(response.getBody(), gilderoy);
	}
	@Test
	public void shouldReturnStatusNotFoundFromMethodEditStudent() {
		Faculty faculty = facultyService.getFacultyById(3L).get();
		Student gilderoy = new Student(150L, "Gilderoy", 27, faculty);
		HttpEntity<Student> entity = new HttpEntity<>(gilderoy);
		ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT
				, entity, Student.class);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void shouldCorrectResultFromMethodDeleteStudent() {
		Faculty faculty = facultyService.getFacultyById(1L).get();
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


///////////////////////////////////////////Тестирование FacultyController

	@Test
	public void shouldCorrectResultFromMethodCreateFaculty() throws Exception {
		Faculty facultyTest = new Faculty();
		facultyTest.setId(0L);
		facultyTest.setName("TestFaculty");
		facultyTest.setColor("brown");

		Faculty faculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, Faculty.class );
		try {

			assertEquals(facultyTest, faculty);
		} finally {
			facultyRepository.deleteById(faculty.getId());

		}

	}

	@Test
	public void shouldCorrectResultFromMethodGetFacultyById() throws Exception {
		Faculty facultyTest = new Faculty();
		facultyTest.setId(null);
		facultyTest.setName("TestFaculty");
		facultyTest.setColor("brown");
		Long id = facultyRepository.save(facultyTest).getId();

		ResponseEntity<Faculty> response = this.restTemplate.getForEntity("http://localhost:" + port + "/faculty/"+id
				, Faculty.class);

		try {
			assertEquals(response.getStatusCode(), HttpStatus.OK);
			assertEquals(response.getBody().getName(), "TestFaculty");
			assertEquals(response.getBody().getColor(), "brown");
		} finally {
			facultyRepository.deleteById(id);
		}

	}

	@Test
	public void shouldCorrectResultFromMethodGetAllFaculties() throws Exception {
		List<Faculty> facultiesList = new ArrayList<>(facultyRepository.findAll());
		List<String> faculties= new ArrayList<>();
		for (Faculty faculty: facultiesList) {
						faculties.add(faculty.toJson());
		}
		ResponseEntity<List> response =this.restTemplate.getForEntity("http://localhost:" + port + "/faculty", List.class);
		assertEquals(response.getBody().toString(), faculties.toString());
	}

	@Test
	public void shouldCorrectResultFromMethodGetFacultiesByColor() throws Exception {
		Faculty faculty1 = new Faculty(null, "TestFaculty1", "black");
		Faculty faculty2 = new Faculty(null, "TestFaculty2", "green");
		Faculty faculty3 = new Faculty(null, "TestFaculty3", "black");
		Long id1 = facultyRepository.save(faculty1).getId();
		Long id2 = facultyRepository.save(faculty2).getId();
		Long id3 = facultyRepository.save(faculty3).getId();
		List<String> faculties = new ArrayList<>(List.of(faculty1.toJson(), faculty3.toJson()));

		ResponseEntity<List> response = this.restTemplate.getForEntity("http://localhost:" + port
				+ "/faculty/color?color=black", List.class);


		try {
			assertEquals(response.getStatusCode(), HttpStatus.OK);
			assertEquals(response.getBody().toString(), faculties.toString());
		} finally {
			facultyRepository.deleteById(id1);
			facultyRepository.deleteById(id2);
			facultyRepository.deleteById(id3);
		}
	}

	@Test
	public void shouldCorrectResultFromMethodGetFacultiesByNameOrColor() throws Exception {
		Faculty faculty1 = new Faculty(null, "TestFaculty1", "black");
		Faculty faculty2 = new Faculty(null, "TestFaculty2", "green");
		Faculty faculty3 = new Faculty(null, "TestFaculty3", "black");
		Long id1 = facultyRepository.save(faculty1).getId();
		Long id2 = facultyRepository.save(faculty2).getId();
		Long id3 = facultyRepository.save(faculty3).getId();
		List<String> faculties = new ArrayList<>(List.of(faculty1.toJson(), faculty3.toJson()));

		ResponseEntity<List> responseByColor = this.restTemplate.getForEntity("http://localhost:" + port
				+ "/faculty?color=black", List.class);

		ResponseEntity<List> responseByName = this.restTemplate.getForEntity("http://localhost:" + port
				+ "/faculty?name=TestFaculty2", List.class);
		try {
			assertEquals(responseByColor.getStatusCode(), HttpStatus.OK);
			assertEquals(responseByColor.getBody().toString(), faculties.toString());

			assertEquals(responseByName.getStatusCode(), HttpStatus.OK);
			assertEquals(responseByName.getBody().toString(), List.of(faculty2.toJson()).toString());
		} finally {
			facultyRepository.deleteById(id1);
			facultyRepository.deleteById(id2);
			facultyRepository.deleteById(id3);
		}


	}

	@Test
	public void shouldCorrectResultFromMethodGetStudentsOnFacultyById() throws Exception {
		assertNotNull(this.restTemplate.getForEntity("http://localhost:" + port
				+ "/faculty/getStudents/1", String.class));

	}

	@Test
	public void shouldCorrectResultFromMethodEditFaculty() throws Exception {

		Faculty faculty1 = new Faculty(null, "TestFaculty1", "black");
		Long id1 = facultyRepository.save(faculty1).getId();
		Faculty faculty3 = new Faculty(id1, "TestFaculty3", "black");

		this.restTemplate.put("http://localhost:" + port + "/faculty", faculty3, Faculty.class);
		ResponseEntity<Faculty> response = this.restTemplate.getForEntity("http://localhost:" + port + "/faculty/"+id1
				, Faculty.class);
		try {
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(faculty3, response.getBody());
		} finally {
			facultyRepository.deleteById(id1);
		}



	}

	@Test
	public void shouldCorrectResultFromMethodDeleteFaculty() throws Exception {
		Faculty faculty1 = new Faculty(null, "TestFaculty1", "black");
		Long id1 = facultyRepository.save(faculty1).getId();

		HttpEntity<Faculty> entity = new HttpEntity<>(faculty1);
		ResponseEntity<Faculty> response = this.restTemplate.exchange("http://localhost:" + port + "/faculty/"+id1, HttpMethod.DELETE
				, null, Faculty.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(faculty1, response.getBody());
	}









}
