package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {

        return facultyRepository.save(faculty);

    }

    public Optional<Faculty> getFaculty(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new FacultyNotFoundException("Faculty not found");
        }
        return facultyRepository.findById(id);

    }

    public Collection<Faculty> getAll() {

        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {

        return facultyRepository.findAll().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> getByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (!facultyRepository.existsById(faculty.getId())) {
            throw new FacultyNotFoundException("Faculty not found");
        }
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> deleteFaculty(Long id) {
        Optional<Faculty> faculty;
        if (!facultyRepository.existsById(id)) {
            throw new FacultyNotFoundException("Faculty not found");
        }
        faculty = facultyRepository.findById(id);
        facultyRepository.deleteById(id);
        return faculty;


    }


    public void clear() {
        facultyRepository.deleteAll();
    }


}
