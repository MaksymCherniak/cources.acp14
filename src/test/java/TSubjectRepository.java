import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import week4.home.study.dao.repositories.SubjectRepository;
import week4.home.study.entity.Subject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"jdbc.url=jdbc:mysql://localhost:3306/studiestest", "hibernate.hbm2ddl.auto=create-drop"})
@ContextConfiguration("classpath:spring/testApplicationConfig.xml")
public class TSubjectRepository {
    private Subject subject;
    private Subject updated;
    @Autowired
    private SubjectRepository subjectRepository;

    @Before
    public void initialize() {
        subject = new Subject();
        subject.setName("Test subject name");
        subject.setDescription("Test subject description");
    }

    @After
    public void delete() {
        if (subjectRepository.getSubject(subject.getName(), subject.getDescription()) != null) {
            subjectRepository.delete(subjectRepository.getSubject(subject.getName(), subject.getDescription()).getId());
        }
    }

    @Test
    public void noSubjectEntriesFoundIfNotInDatabase() {
        assertNull("Subject which isn't in database exists", subjectRepository.getSubject(subject.getName(), subject.getDescription()));
    }

    @Test
    public void subjectEntryFoundIfInDatabase() {
        subjectRepository.save(subject);

        assertNotNull("Subject not found", subjectRepository.getSubject(subject.getName(), subject.getDescription()));
    }

    @Test
    public void subjectEntryNotFoundAfterRemoved() {
        subjectRepository.save(subject);

        subjectRepository.delete(subjectRepository.getSubject(subject.getName(), subject.getDescription()).getId());

        assertNull("Subject which removed from database exists", subjectRepository.getSubject(subject.getName(), subject.getDescription()));

    }

    @Test
    public void updatedSubjectInDatabase() {
        subjectRepository.save(subject);

        updated = subjectRepository.getSubject(subject.getName(), subject.getDescription());
        updated.setName("updated subject name");
        updated.setDescription("updated subject description");

        subjectRepository.updateSubject(updated.getName(), updated.getDescription(), updated.getId());

        assertNotNull("Subject not found", subjectRepository.getSubject(updated.getName(), updated.getDescription()));

        subjectRepository.delete(subjectRepository.getSubject(updated.getName(), updated.getDescription()).getId());
    }

    @Test
    public void oldSubjectNotFoundIfUpdated() {
        subjectRepository.save(subject);

        updated = subjectRepository.getSubject(subject.getName(), subject.getDescription());
        updated.setName("updated subject name");
        updated.setDescription("updated subject description");

        subjectRepository.updateSubject(updated.getName(), updated.getDescription(), updated.getId());

        assertNull("Old subject exists", subjectRepository.getSubject(subject.getName(), subject.getDescription()));

        subjectRepository.delete(subjectRepository.getSubject(updated.getName(), updated.getDescription()).getId());
    }
}
