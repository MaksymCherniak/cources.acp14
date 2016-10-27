import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"jdbc.url=jdbc:mysql://localhost:3306/studiestest", "hibernate.hbm2ddl.auto=create-drop"})
@ContextConfiguration("classpath:spring/testApplicationConfig.xml")
@Transactional
public class TStudentDao {
    private static Logger log = Logger.getLogger(TStudentDao.class.getName());
    @Autowired
    private IStudentDao iStudentDao;
    @Autowired
    private IGroupDao iGroupDao;

    private Student student;
    private Student updated;
    private Groups groups;

    @Before
    public void initialize() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        student = new Student();
        groups = new Groups();

        groups.setName("Test group");
        iGroupDao.addGroup(groups);

        student.setName("Test student");
        student.setGroups(iGroupDao.getGroup(groups));
    }

    @After
    public void delete() {
        try {
            iStudentDao.removeStudent(iStudentDao.getStudent(student).getId());
        } catch (EntityNotFoundException e) {
        }
    }

    @Test
    public void addStudentIsPossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        assertTrue("Student didn't add", iStudentDao.addStudent(student));
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void addDuplicateStudentIsImpossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iStudentDao.addStudent(student);

        iStudentDao.addStudent(student);
    }

    @Test
    public void studentExistsAfterAdd() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {
        iStudentDao.addStudent(student);

        assertNotNull("Student not found", iStudentDao.getStudent(student));
    }

    @Test(expected = EntityNotFoundException.class)
    public void studentDoesNotExistAfterRemove() throws ComingNullObjectException, OperationFailedException
            , EntityNotFoundException, EntityAlreadyExistException {
        iStudentDao.addStudent(student);

        iStudentDao.removeStudent(iStudentDao.getStudent(student).getId());

        assertNull("Student exists after remove", iStudentDao.getStudent(student));
    }

    @Test
    public void checkTotalStudents() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException
            , EntityNotFoundException {

        iStudentDao.addStudent(student);

        assertThat(iStudentDao.getAllStudents(0, 10).size(), is(1));
    }

    @Test
    public void updateStudentIsPossible() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        iStudentDao.addStudent(student);

        updated = iStudentDao.getStudent(student);
        updated.setName("Updated student");

        assertNotNull("Student didn't update", iStudentDao.getStudent(updated));

        iStudentDao.removeStudent(iStudentDao.getStudent(updated).getId());
    }

    @Test
    public void studentUpdated() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        iStudentDao.addStudent(student);

        updated = iStudentDao.getStudent(student);
        updated.setName("Updated student");

        assertEquals("Student didn't update", updated, iStudentDao.getStudent(updated));

        iStudentDao.removeStudent(iStudentDao.getStudent(updated).getId());
    }

    @Test
    public void checkGetStudentsByGroup() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        iStudentDao.addStudent(student);

        assertThat(iStudentDao.getStudentsByGroup(iGroupDao.getGroup(groups), 10).size(), is(1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void canNotRemoveIfStudentNotExists() throws EntityNotFoundException {
        iStudentDao.removeStudent(iStudentDao.getStudent(student).getId());
    }
}
