package week4.home.study.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups groups;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, Groups groups) {
        this.name = name;
        this.groups = groups;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Student{" +
                ", name='" + name + '\'' +
                ", groups=" + groups.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(groups, student.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, groups);
    }
}
