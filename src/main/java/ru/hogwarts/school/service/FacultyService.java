package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Optional<Faculty> getFacultyById(Long id);

    Optional<Faculty> deleteFaculty(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getByNameOrColor(String name, String color);

    Collection<Student> getStudentsOnFacultyById(Long id);

    Faculty editFaculty(Faculty faculty);

    Collection<Faculty> getFacultiesByColor(String name);

    void clear();

}
