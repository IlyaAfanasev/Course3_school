package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  Long id;



    private  String name;

    private  String color;


    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private Set<Student> students;

    public Faculty() {
    }

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(name, faculty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "id=" +  id + ", " +
                "name=" + name  + ", "
                + "color="  + color  +
                        '}';

        }

}
