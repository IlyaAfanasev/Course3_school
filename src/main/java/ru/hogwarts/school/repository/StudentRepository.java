package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int fromAge, int toAge);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int getNumberOfStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    int getAverageAgeOfStudents();


    @Query(value = "SELECT * From student Order by id Desc Limit 5", nativeQuery = true)
    List<Student> getLast_5_Student();

}
