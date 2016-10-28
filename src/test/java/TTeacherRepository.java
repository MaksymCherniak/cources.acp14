import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import week4.home.study.dao.repositories.SubjectRepository;
import week4.home.study.dao.repositories.TeacherRepository;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"jdbc.url=jdbc:mysql://localhost:3306/studiestest", "hibernate.hbm2ddl.auto=create-drop"})
@ContextConfiguration("classpath:spring/testApplicationConfig.xml")
public class TTeacherRepository {
    private Teacher teacher;
    private Teacher updated;
    private Subject subject;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Before
    public void initialize() {
        teacher = new Teacher();
        subject = new Subject();

        subject.setName("Math");
        subject.setDescription("Description");
        if (subjectRepository.getSubject(subject.getName(), subject.getDescription()) == null) {
            subjectRepository.save(subject);
        }

        teacher.setName("Teacher");
        teacher.setExperience(2);
        teacher.setSubject(subjectRepository.getSubject(subject.getName(), subject.getDescription()));
    }

    @After
    public void delete() {
        if (teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId()) != null) {
            teacherRepository.delete(teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(),
                    teacher.getSubject().getId()).getId());
        }
    }

    @Test
    public void noTeacherEntriesFoundIfNotInDatabase() {
        assertNull("Teacher which isn't in database exists", teacherRepository.getTeacher(teacher.getName(),
                teacher.getExperience(), teacher.getSubject().getId()));
    }

    @Test
    public void teacherEntryFoundIfInDatabase() {
        teacherRepository.save(teacher);

        assertNotNull("Teacher not found", teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(),
                teacher.getSubject().getId()));
    }

    @Test
    public void teacherEntryNotFoundAfterRemoved() {
        teacherRepository.save(teacher);

        teacherRepository.delete(teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(),
                teacher.getSubject().getId()).getId());

        assertNull("Teacher which removed from database exists", teacherRepository.getTeacher(teacher.getName(),
                teacher.getExperience(), teacher.getSubject().getId()));

    }

    @Test
    public void updatedTeacherInDatabase() {
        teacherRepository.save(teacher);

        updated = teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId());
        updated.setName("updated teacher name");
        updated.setExperience(5);

        teacherRepository.updateTeacher(updated.getName(), updated.getExperience(), updated.getSubject().getId(), updated.getId());

        assertNotNull("Teacher not found", teacherRepository.getTeacher(updated.getName(), updated.getExperience(),
                updated.getSubject().getId()));

        teacherRepository.delete(teacherRepository.getTeacher(updated.getName(), updated.getExperience(),
                updated.getSubject().getId()).getId());
    }

    @Test
    public void oldTeacherNotFoundIfUpdated() {
        teacherRepository.save(teacher);

        updated = teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId());
        updated.setName("updated teacher name");
        updated.setExperience(5);

        teacherRepository.updateTeacher(updated.getName(), updated.getExperience(), updated.getSubject().getId(), updated.getId());

        assertNull("Old teacher exists", teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(),
                teacher.getSubject().getId()));

        teacherRepository.delete(teacherRepository.getTeacher(updated.getName(), updated.getExperience(),
                updated.getSubject().getId()).getId());
    }

    @Test
    public void checkGetTeachersBySubject() {
        teacherRepository.save(teacher);

        assertThat(teacherRepository.getTeachersBySubject(subjectRepository.getSubject(subject.getName(),
                subject.getDescription()).getId()).size(), is(1));
    }

    @Test
    public void checkGetTeachersByExperience() {
        teacherRepository.save(teacher);

        assertThat(teacherRepository.getTeachersMoreThanExperience(teacher.getExperience()).size(), is(1));
    }

    @Test
    public void checkGetMaxExperiencedTeachers() {
        teacherRepository.save(teacher);

        assertThat(teacherRepository.getMaxExpiriencedTeachers().size(), is(1));
    }

    @Test
    public void checkGetMinExperiencedTeachers() {
        teacherRepository.save(teacher);

        assertThat(teacherRepository.getMinExpiriencedTeachers().size(), is(1));
    }
}
