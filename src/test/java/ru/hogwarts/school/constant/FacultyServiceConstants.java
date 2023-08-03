package ru.hogwarts.school.constant;

import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FacultyServiceConstants {

    public static final Faculty FACULTY_1 = new Faculty(1L, "Faculty1", "red");
    public static final Faculty FACULTY_2 = new Faculty(2L, "Faculty2", "green");
    public static final Faculty FACULTY_3 = new Faculty(3L, "Faculty3", "red");

    public static final Collection <Faculty> COLLECTIONS_FACULTIES_BY_RED_COLOR
            = new ArrayList<>(List.of(FACULTY_1,FACULTY_3));

    public static final Collection<Faculty> COLLECTIONS_FACULTIES
            = Collections.unmodifiableList(new ArrayList<>(List.of(FACULTY_1, FACULTY_2, FACULTY_3)));


}
