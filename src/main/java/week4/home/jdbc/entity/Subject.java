package week4.home.jdbc.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subjects")
@NamedQuery(name = "Subject.getAll", query = "SELECT g from subjects g LIMIT 100")
public class Subject {
    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "subject")
    private Set<Teacher> teachers;
    @ManyToMany(mappedBy="subjects")
    private Set<Group> groups;

    public Subject() {
    }

    public Subject(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
