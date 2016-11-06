package week4.home.study.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    private List<Teacher> teachers;
    @ManyToMany(mappedBy="subjects", fetch = FetchType.EAGER)
    private List<Groups> groupses;

    public Subject() {
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Groups> getGroupses() {
        return groupses;
    }

    public void setGroupses(List<Groups> groupses) {
        this.groupses = groupses;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (id != subject.id) return false;
        if (!name.equals(subject.name)) return false;
        return description.equals(subject.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
