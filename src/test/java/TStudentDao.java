import org.apache.log4j.Logger;
import org.junit.*;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.impls.mysql.GroupDaoImpl;
import week4.home.study.dao.impls.mysql.StudentDaoImpl;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TStudentDao {
    private static Logger log = Logger.getLogger(TStudentDao.class.getName());
    private static IStudentDao iStudentDao;
    private static IGroupDao iGroupDao;

    private Student student;
    private static Groups groups;

    private static EntityManager entityManager;

    @BeforeClass
    public static void initializeConnection() throws OperationFailedException, EntityAlreadyExistException, ComingNullObjectException {
        entityManager = HibernateUtil.getTestEm();
        iStudentDao = new StudentDaoImpl(entityManager);
        iGroupDao = new GroupDaoImpl(entityManager);

        groups = new Groups();
        groups.setName("Test group");

        iGroupDao.addGroup(groups);
    }

    @AfterClass
    public static void shutdownConnection() {
        entityManager.clear();
        entityManager.close();
    }

    @Before
    public void initialize() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        student = new Student();
        student.setName("Test students");
        student.setGroups(iGroupDao.getGroup(groups));
    }

    @After
    public void delete() throws EntityNotFoundException {
        Student st = iStudentDao.getStudent(student);

        if (st != null) {
            iStudentDao.removeStudent(st.getId());
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
    public void studentExistsAfterAdd() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iStudentDao.addStudent(student);

        assertNotNull("Student not found", iStudentDao.getStudent(student));
    }

    @Test
    public void studentDoesNotExistAfterRemove() throws ComingNullObjectException, OperationFailedException
            , EntityNotFoundException, EntityAlreadyExistException {
        iStudentDao.addStudent(student);

        iStudentDao.removeStudent(iStudentDao.getStudent(student).getId());

        assertNull("Student exists after remove", iStudentDao.getStudent(student));
    }

    @Test
    public void checkTotalStudents()throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iStudentDao.addStudent(student);

        assertThat(iStudentDao.getAllStudents(0, 10).size(), is(1));
    }

    @Test
    public void updateStudentIsPossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iStudentDao.addStudent(student);

        Student newStudent = iStudentDao.getStudent(student);
        newStudent.setName("Updated student");

        assertTrue("Student didn't update", iStudentDao.updateStudent(newStudent));
    }

    @Test
    public void studentUpdated() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iStudentDao.addStudent(student);

        Student newStudent = iStudentDao.getStudent(student);
        newStudent.setName("Updated student");

        iStudentDao.updateStudent(newStudent);

        assertEquals("Student didn't update", newStudent, iStudentDao.getStudent(newStudent));
    }

    @Test
    public void checkGetStudentsByGroup() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iStudentDao.addStudent(student);

        assertThat(iStudentDao.getStudentsByGroup(iGroupDao.getGroup(groups), 10).size(), is(1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void canNotRemoveIfStudentNotExists() throws EntityNotFoundException {
        iStudentDao.removeStudent(student.getId());
    }
}
