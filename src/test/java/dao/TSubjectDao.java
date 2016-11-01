package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"jdbc.url=jdbc:mysql://localhost:3306/studiestest", "hibernate.hbm2ddl.auto=create-drop"})
@ContextConfiguration("classpath:spring/testApplicationConfig.xml")
@Transactional
public class TSubjectDao {
    private static Logger log = Logger.getLogger(TSubjectDao.class.getName());
    @Autowired
    private ISubjectDao iSubjectDao;

    private Subject subject;
    private Subject updated;

    @Before
    public void initialize() {
        subject = new Subject();
        subject.setName("Test name");
        subject.setDescription("Test description");
    }

    @After
    public void delete() throws EntityNotFoundException {
        try {
            iSubjectDao.removeSubject(iSubjectDao.getSubject(subject).getId());
        } catch (EntityNotFoundException e) {
        }
    }

    @Test
    public void addSubjectIsPossible() throws ComingNullObjectException, EntityAlreadyExistException {
        assertTrue("Subject didn't add", iSubjectDao.addSubject(subject));
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void addDuplicateSubjectIsImpossible() throws ComingNullObjectException, EntityAlreadyExistException {
        iSubjectDao.addSubject(subject);

        iSubjectDao.addSubject(subject);
    }

    @Test
    public void subjectExistsAfterAdd() throws ComingNullObjectException, EntityAlreadyExistException, EntityNotFoundException {

        iSubjectDao.addSubject(subject);

        assertNotNull("Subject not found", iSubjectDao.getSubject(subject));
    }

    @Test(expected = EntityNotFoundException.class)
    public void subjectDoesNotExistAfterRemove() throws ComingNullObjectException, EntityNotFoundException, EntityAlreadyExistException {

        iSubjectDao.addSubject(subject);

        iSubjectDao.removeSubject(iSubjectDao.getSubject(subject).getId());

        assertNull("Subject exists after remove", iSubjectDao.getSubject(subject));
    }

    @Test
    public void checkTotalSubjects() throws ComingNullObjectException, EntityAlreadyExistException
            , EntityNotFoundException {

        iSubjectDao.addSubject(subject);

        assertThat(iSubjectDao.getAllSubjects(0, 10).size(), is(1));
    }

    @Test
    public void updateSubjectIsPossible() throws ComingNullObjectException, EntityAlreadyExistException, EntityNotFoundException {

        iSubjectDao.addSubject(subject);

        updated = iSubjectDao.getSubject(subject);
        updated.setName("Updated subject");
        updated.setDescription("Updated description");

        assertNotNull("Subject didn't update", iSubjectDao.getSubject(updated));

        iSubjectDao.removeSubject(iSubjectDao.getSubject(updated).getId());
    }

    @Test
    public void subjectUpdated() throws ComingNullObjectException, EntityAlreadyExistException, EntityNotFoundException {

        iSubjectDao.addSubject(subject);

        updated = iSubjectDao.getSubject(subject);
        updated.setName("Updated subject");
        updated.setDescription("Updated description");

        assertEquals("Subject didn't update", updated, iSubjectDao.getSubject(updated));

        iSubjectDao.removeSubject(iSubjectDao.getSubject(updated).getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void canNotRemoveIfSubjectNotExists() throws EntityNotFoundException {
        iSubjectDao.removeSubject(iSubjectDao.getSubject(subject).getId());
    }

    @Test
    public void checkGetSubjectByName() throws EntityAlreadyExistException, ComingNullObjectException
            , EntityNotFoundException {

        iSubjectDao.addSubject(subject);

        assertNotNull("Subject not found", iSubjectDao.getSubjectByName(subject.getName()));
    }

    @Test(expected = ComingNullObjectException.class)
    public void addSubjectIfComingNullObjectThrowException() throws EntityAlreadyExistException, ComingNullObjectException {
        iSubjectDao.addSubject(null);
    }

    @Test(expected = ComingNullObjectException.class)
    public void updateSubjectIfComingNullObjectThrowException() throws EntityAlreadyExistException, ComingNullObjectException {
        iSubjectDao.updateSubject(null);
    }
}
