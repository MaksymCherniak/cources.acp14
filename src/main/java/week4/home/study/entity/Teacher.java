package week4.home.study.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teachers")
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

    public Teacher(String name, int experience, Subject subject) {
        this.name = name;
        this.experience = experience;
        this.subject = subject;
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
        return experience == teacher.experience &&
                Objects.equals(name, teacher.name) &&
                Objects.equals(subject, teacher.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, experience, subject);
    }
}
