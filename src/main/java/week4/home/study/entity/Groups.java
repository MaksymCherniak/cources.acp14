package week4.home.study.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups")
public class Groups {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @OneToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private List<Student> students;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "study",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "subject_id"))
    private List<Subject> subjects;

    public Groups() {
    }

    public Groups(String name) {
        this.name = name;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Subject> getSubjects() {
        if (subjects == null) {
            subjects = new ArrayList<>();
        }
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return Objects.equals(name, groups.name) &&
                Objects.equals(students, groups.students) &&
                Objects.equals(subjects, groups.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
