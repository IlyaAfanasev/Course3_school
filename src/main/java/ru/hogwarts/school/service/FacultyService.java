package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Optional<Faculty> getFaculty(Long id);

    Optional<Faculty> deleteFaculty(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getByNameOrColor(String name, String color);

    Faculty editFaculty(Faculty faculty);

    Collection<Faculty> getFacultiesByColor(String name);

    void clear();

}
