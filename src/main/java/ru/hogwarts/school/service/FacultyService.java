package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties;

    private Long facultyId = 1L;

    public FacultyService() {
        this.faculties = new HashMap<>();
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(facultyId);
        faculties.put(facultyId, faculty);
        facultyId++;
        return faculty;

    }

    public Faculty getFaculty(Long id) {

        return faculties.get(id);

    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {

             faculties.put(faculty.getId(), faculty);

            return faculties.get(faculty.getId());
        }
        return null;
    }

    public Faculty deleteFaculty(Long id) {


            return faculties.remove(id);


    }

    public Collection<Faculty> getAll() {

        return Collections.unmodifiableList(new ArrayList<>(faculties.values()));
    }

    public Collection<Faculty> getFacultiesByColor(String color) {

        return faculties.values().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }


}
