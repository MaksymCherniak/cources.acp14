import org.junit.*;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.impls.mysql.SubjectDaoImpl;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TSubjectDao {
    private static Logger log = Logger.getLogger(TSubjectDao.class.getName());
    private static ISubjectDao iSubjectDao;
    private Subject subject;
    private Subject updatedSubject;

    private static EntityManager entityManager;

    @BeforeClass
    public static void initializeConnection() {
        entityManager = HibernateUtil.getTestEm();
        iSubjectDao = new SubjectDaoImpl(entityManager);
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
    public void delete() throws EntityNotFoundException {
        Subject sub = iSubjectDao.getSubject(subject);

        if (sub != null) {
            iSubjectDao.removeSubject(sub.getId());
        }
    }

    @Test
    public void addSubjectIsPossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        assertTrue("Subject didn't add", iSubjectDao.addSubject(subject));
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void addDuplicateSubjectIsImpossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iSubjectDao.addSubject(subject);

        iSubjectDao.addSubject(subject);
    }

    @Test
    public void subjectExistsAfterAdd() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iSubjectDao.addSubject(subject);

        assertNotNull("Subject not found", iSubjectDao.getSubject(subject));
    }

    @Test
    public void subjectDoesNotExistAfterRemove() throws ComingNullObjectException, OperationFailedException
            , EntityNotFoundException, EntityAlreadyExistException {
        iSubjectDao.addSubject(subject);

        iSubjectDao.removeSubject(iSubjectDao.getSubject(subject).getId());

        assertNull("Subject exists after remove", iSubjectDao.getSubject(subject));
    }

    @Test
    public void checkTotalSubjects() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iSubjectDao.addSubject(subject);

        assertThat(iSubjectDao.getAllSubjects(0, 10).size(), is(1));
    }

    @Test
    public void updateSubjectIsPossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iSubjectDao.addSubject(subject);

        updatedSubject = iSubjectDao.getSubject(subject);
        updatedSubject.setName("Updated subject");
        updatedSubject.setDescription("Updated description");

        assertTrue("Subject didn't update", iSubjectDao.updateSubject(updatedSubject));
    }

    @Test
    public void subjectUpdated() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iSubjectDao.addSubject(subject);

        updatedSubject = iSubjectDao.getSubject(subject);
        updatedSubject.setName("Updated subject");
        updatedSubject.setDescription("Updated description");

        iSubjectDao.updateSubject(updatedSubject);

        assertEquals("Subject didn't update", updatedSubject, iSubjectDao.getSubject(updatedSubject));
    }

    @Test(expected = EntityNotFoundException.class)
    public void canNotRemoveIfSubjectNotExists() throws EntityNotFoundException {
        iSubjectDao.removeSubject(subject.getId());
    }
}
