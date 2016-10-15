package week4.home.study.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Groups {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "groups")
    private Set<Student> students;
    @ManyToMany
    @JoinTable(
            name="study",
            joinColumns=@JoinColumn(name="group_id", referencedColumnName="group_id"),
            inverseJoinColumns=@JoinColumn(name="subject_id", referencedColumnName="subject_id"))
    private Set<Subject> subjects;

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

    @Override
    public String toString() {
        return "Groups{" +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Groups other = (Groups) obj;

        return name.equals(other.getName()) && id == other.getId();
    }
}
