package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import java.util.Collection;


@RestController
@RequestMapping("faculty")

public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity <Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity <Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.deleteFaculty(id);
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(faculty);
    }

    @GetMapping

    public Collection <Faculty> geAll() {
        return facultyService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/color")
    public Collection<Faculty> getFacultiesByColor(@RequestParam ("color") String color) {
        return facultyService.getFacultiesByColor(color);
    }
}
