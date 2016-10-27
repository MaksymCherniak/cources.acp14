import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import week4.home.study.dao.repositories.GroupRepository;
import week4.home.study.dao.repositories.StudentRepository;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"jdbc.url=jdbc:mysql://localhost:3306/studiestest", "hibernate.hbm2ddl.auto=create-drop"})
@ContextConfiguration("classpath:spring/testApplicationConfig.xml")
public class TStudentRepository {
    private Student student;
    private Student updated;
    private Groups groups;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Before
    public void initialize() {
        student = new Student();
        groups = new Groups();

        groups.setName("acp1");
        if (groupRepository.getGroupByName(groups.getName()) == null) {
            groupRepository.save(groups);
        }

        student.setName("Test student name");
        student.setGroups(groupRepository.getGroupByName(groups.getName()));
    }

    @After
    public void delete() {
        if (studentRepository.getStudent(student.getName(), student.getGroups().getId()) != null) {
            studentRepository.delete(studentRepository.getStudent(student.getName(), student.getGroups().getId()).getId());
        }
    }

    @Test
    public void noStudentEntriesFoundIfNotInDatabase() {
        assertNull("Student which isn't in database exists", studentRepository.getStudent("name", 1));
    }

    @Test
    public void studentEntryFoundIfInDatabase() {
        studentRepository.save(student);

        assertNotNull("Student not found", studentRepository.getStudent(student.getName(), student.getGroups().getId()));
    }

    @Test
    public void studentEntryNotFoundAfterRemoved() {
        studentRepository.save(student);

        studentRepository.delete(studentRepository.getStudent(student.getName(), student.getGroups().getId()).getId());

        assertNull("Student which removed from database exists", studentRepository.getStudent(student.getName(), student.getGroups().getId()));

    }

    @Test
    public void updatedStudentInDatabase() {
        studentRepository.save(student);

        updated = studentRepository.getStudent(student.getName(), student.getGroups().getId());
        updated.setName("updated student name");

        studentRepository.updateStudent(updated.getName(), updated.getGroups().getId(), updated.getId());

        assertNotNull("Student not found", studentRepository.getStudent(updated.getName(), updated.getGroups().getId()));

        studentRepository.delete(studentRepository.getStudent(updated.getName(), updated.getGroups().getId()));
    }

    @Test
    public void oldStudentNotFoundIfUpdated() {
        studentRepository.save(student);

        updated = studentRepository.getStudent(student.getName(), student.getGroups().getId());
        updated.setName("updated student name");

        studentRepository.updateStudent(updated.getName(), updated.getGroups().getId(), updated.getId());

        assertNull("Old student exists", studentRepository.getStudent(student.getName(), student.getGroups().getId()));

        studentRepository.delete(studentRepository.getStudent(updated.getName(), updated.getGroups().getId()));
    }

    @Test
    public void checkGetStudentsByGroup() {
        studentRepository.save(student);

        assertThat(studentRepository.getStudentsByGroup(groupRepository.getGroupByName(groups.getName()).getId()).size(), is(1));
    }
}
