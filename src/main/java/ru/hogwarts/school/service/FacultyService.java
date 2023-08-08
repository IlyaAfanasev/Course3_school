package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Optional<Faculty> getFaculty(Long id);

    Faculty editFaculty(Faculty faculty);

    Optional<Faculty> deleteFaculty(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getFacultiesByColor(String color);

    void clear();

}
