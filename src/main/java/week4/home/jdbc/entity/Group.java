package week4.home.jdbc.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "groups")
@NamedQuery(name = "Group.getAll", query = "SELECT g from groups g LIMIT 100")
public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "group")
    private Set<Student> students;
    @ManyToMany
    @JoinTable(
            name="study",
            joinColumns=@JoinColumn(name="group_id", referencedColumnName="group_id"),
            inverseJoinColumns=@JoinColumn(name="subject_id", referencedColumnName="subject_id"))
    private Set<Subject> subjects;

    public Group() {
    }

    public Group(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
