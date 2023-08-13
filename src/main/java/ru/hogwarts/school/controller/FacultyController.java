package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.FacultyServiceImpl;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("faculty")

public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Faculty>> getFaculty(@PathVariable Long id) {
        Optional<Faculty> faculty = facultyService.getFaculty(id);

        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/color")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@RequestParam ("color") String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultiesByColor(color));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping

    public Collection <Faculty> geAll(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String color) {
        if (name != null || color != null) {
            return facultyService.getByNameOrColor(name, color);
        }
        return facultyService.getAll();
    }

    @GetMapping("/getStudents/{id}")
    public Collection<Student> getStudents(@PathVariable Long id) {
        Collection<Student> students = facultyService.getStudentsOnFacultyById(id);

        return students;
    }


    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity <Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);

        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity <Optional<Faculty>> deleteFaculty(@PathVariable Long id) {
        Optional<Faculty> faculty = facultyService.deleteFaculty(id);


        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping
    public ResponseEntity clear() {
        facultyService.clear();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
