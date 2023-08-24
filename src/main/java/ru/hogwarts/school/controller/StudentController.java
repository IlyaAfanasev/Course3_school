package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("student")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity <Optional<Student>> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudent(id);

        return ResponseEntity.ok(student);
    }
    @GetMapping("/getfaculty/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        Faculty faculty = studentService.getFacultyByStudentId(id);

        return faculty;
    }

    @GetMapping

    public Collection<Student> geAll(@RequestParam(required = false) Integer fromAge,
                                     @RequestParam(required = false) Integer toAge) {
        if (fromAge != null && toAge != null) {
            return studentService.findByAgeBetween(fromAge,toAge);
        }

        return studentService.getAll();
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam int age) {

        if (age <= 0) {

            return ResponseEntity.badRequest().build();
        }
        if (studentService.getStudentsByAge(age).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

            return ResponseEntity.ok(studentService.getStudentsByAge(age));


    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);

        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity <Optional<Student>> deleteStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.deleteStudent(id);


        return ResponseEntity.ok(student);
    }

    @DeleteMapping
    public ResponseEntity clear() {
        studentService.clear();
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
