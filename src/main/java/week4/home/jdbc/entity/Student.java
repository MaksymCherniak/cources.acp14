package week4.home.jdbc.entity;

import javax.persistence.*;

@Entity
@Table(name = "students")
@NamedQuery(name = "Student.getAll", query = "SELECT g from students g LIMIT 100")
public class Student {
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Student() {
    }

    public Student(long id, String name, Group group) {
        this.id = id;
        this.name = name;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group=" + group.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (!group.equals(student.getGroup())) return false;
        return name != null ? name.equals(student.name) : student.name == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (group.getId() ^ (group.getId() >>> 32));
        return result;
    }
}
