package week4.home.jdbc.entity;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
@NamedQuery(name = "Teacher.getAll", query = "SELECT g from teachers g LIMIT 100")
public class Teacher {
    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "experience")
    private int experience;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Teacher() {
    }

    public Teacher(long id, String name, int experience, Subject subject) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.subject = subject;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", experience=" + experience +
                ", subject=" + subject.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (id != teacher.id) return false;
        if (experience != teacher.experience) return false;
        if (!subject.equals(teacher.getSubject())) return false;
        return name != null ? name.equals(teacher.name) : teacher.name == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + experience;
        result = 31 * result + (int) (subject.getId() ^ (subject.getId() >>> 32));
        return result;
    }
}
