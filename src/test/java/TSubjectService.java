import org.junit.*;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.impls.mysql.StudentServiceImpl;
import week4.home.study.dao.services.ISubjectService;
import week4.home.study.dao.services.ServiceFactory;
import week4.home.study.entity.Subject;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TSubjectService {
    private static Logger log = Logger.getLogger(TSubjectService.class.getName());
    private ISubjectService iSubjectService = ServiceFactory.getSubjectInstance();
    private Subject subject;
    private Subject updatedSubject;

    private static EntityManager entityManager;

    @BeforeClass
    public static void initializeConnection() {
        entityManager = HibernateUtil.getTestEm();
        StudentServiceImpl.setEntityManager(entityManager);
    }

    @AfterClass
    public static void shutdownConnection() {
        entityManager.clear();
        entityManager.close();
    }

    @Before
    public void initialize() {
        subject = new Subject();
        subject.setName("Test name");
        subject.setDescription("Test description");
    }

    @After
    public void delete() {
        Subject sub = iSubjectService.getSubject(subject);

        if (sub != null) {
            iSubjectService.removeSubject(sub.getId());
        }
    }

    @Test
    public void addSubjectIsPossible() {
        assertTrue("Subject didn't add", iSubjectService.addSubject(subject));
    }

    @Test
    public void addDuplicateSubjectIsImpossible() {
        iSubjectService.addSubject(subject);

        assertFalse("Duplicate subject added", iSubjectService.addSubject(subject));
    }

    @Test
    public void subjectExistsAfterAdd() {
        iSubjectService.addSubject(subject);

        assertNotNull("Subject not found", iSubjectService.getSubject(subject));
    }

    @Test
    public void subjectDoesNotExistAfterRemove() {
        iSubjectService.addSubject(subject);

        iSubjectService.removeSubject(iSubjectService.getSubject(subject).getId());

        assertNull("Subject exists after remove", iSubjectService.getSubject(subject));
    }

    @Test
    public void checkTotalSubjects() {
        iSubjectService.addSubject(subject);

        for (Subject subject : iSubjectService.getAllSubjects()) {
            System.out.println(subject.toString());
        }
        assertThat(iSubjectService.getAllSubjects().size(), is(1));
    }

    @Test
    public void updateSubjectIsPossible() {
        iSubjectService.addSubject(subject);

        updatedSubject = iSubjectService.getSubject(subject);
        updatedSubject.setName("Updated subject");
        updatedSubject.setDescription("Updated description");

        assertTrue("Subject didn't update", iSubjectService.updateSubject(updatedSubject));
    }

    @Test
    public void subjectUpdated() {
        iSubjectService.addSubject(subject);

        updatedSubject = iSubjectService.getSubject(subject);
        updatedSubject.setName("Updated subject");
        updatedSubject.setDescription("Updated description");

        iSubjectService.updateSubject(updatedSubject);

        assertEquals("Subject didn't update", updatedSubject, iSubjectService.getSubject(updatedSubject));
    }
}
