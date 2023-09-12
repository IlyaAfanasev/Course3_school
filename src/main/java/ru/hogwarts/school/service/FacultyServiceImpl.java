package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");

        return facultyRepository.save(faculty);

    }

    public Optional<Faculty> getFacultyById(Long id) {
        logger.info("Was invoked method for get faculty by id");
        if (!facultyRepository.existsById(id)) {
            logger.error("There is not faculty with id = " + id);
            throw new FacultyNotFoundException("Faculty not found");
        }
        return facultyRepository.findById(id);

    }

    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method for get faculty by color");

        return facultyRepository.findAll().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> getByNameOrColor(String name, String color) {
        logger.info("Was invoked method for get faculty by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getStudentsOnFacultyById(Long id) {
        logger.info("Was invoked method for get students by faculty_id");
        if (!facultyRepository.existsById(id)) {
            logger.error("There is not faculty with id = " + id);
            throw new FacultyNotFoundException("Faculty not found");
        }
        return facultyRepository.findById(id).get().getStudents();
    }
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        if (!facultyRepository.existsById(faculty.getId())) {
            logger.error("There is not faculty with id = " + faculty.getId());
            throw new FacultyNotFoundException("Faculty not found");
        }
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        Optional<Faculty> faculty;
        if (!facultyRepository.existsById(id)) {
            logger.error("There is not faculty with id = " + id);
            throw new FacultyNotFoundException("Faculty not found");
        }
        faculty = facultyRepository.findById(id);
        facultyRepository.deleteById(id);
        return faculty;


    }


    public void clear() {
        logger.info("Was invoked method for clear table faculty");
        facultyRepository.deleteAll();
    }

    @Override
    public String getLongestNameOfFaculty() {
        logger.info("Was invoked method for get Faculty with the longest name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).get();

    }


}
