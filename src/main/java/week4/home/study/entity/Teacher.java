package week4.home.study.entity;

import javax.persistence.*;

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

        Teacher other = (Teacher) obj;

        return name.equals(other.getName()) && id == other.getId() && experience == other.getExperience()
                && subject.equals(other.getSubject());
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
