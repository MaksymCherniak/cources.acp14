import org.apache.log4j.Logger;
import org.junit.*;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.impls.mysql.SubjectDaoImpl;
import week4.home.study.dao.impls.mysql.TeacherDaoImpl;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TTeacherDao {
    private static Logger log = Logger.getLogger(TTeacherDao.class.getName());
    private static ITeacherDao iTeacherDao;
    private static ISubjectDao iSubjectDao;

    private static Subject subject;
    private Teacher teacher;
    private Teacher updatedTeacher;

    private static EntityManager entityManager;

    @BeforeClass
    public static void initializeConnection() throws OperationFailedException, EntityAlreadyExistException, ComingNullObjectException {
        entityManager = HibernateUtil.getTestEm();
        iSubjectDao = new SubjectDaoImpl(entityManager);
        iTeacherDao = new TeacherDaoImpl(entityManager);

        subject = new Subject();
        subject.setName("Test subject");
        subject.setDescription("Test description");

        iSubjectDao.addSubject(subject);
    }

    @AfterClass
    public static void shutdownConnection() {
        entityManager.clear();
        entityManager.close();
    }

    @Before
    public void initialize() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        teacher = new Teacher();
        teacher.setName("Test teacher");
        teacher.setExperience(5);
        teacher.setSubject(iSubjectDao.getSubject(subject));
    }

    @After
    public void delete() throws EntityNotFoundException {
        Teacher teach = iTeacherDao.getTeacher(teacher);

        if (teach != null) {
            iTeacherDao.removeTeacher(teach.getId());
        }
    }

    @Test
    public void addTeacherIsPossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        assertTrue("Teacher didn't add", iTeacherDao.addTeacher(teacher));
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void addDuplicateTeacherIsImpossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        iTeacherDao.addTeacher(teacher);
    }

    @Test
    public void teacherExistsAfterAdd() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        assertNotNull("Teacher not found", iTeacherDao.getTeacher(teacher));
    }

    @Test
    public void teacherDoesNotExistAfterRemove() throws ComingNullObjectException, OperationFailedException
            , EntityNotFoundException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        iTeacherDao.removeTeacher(iTeacherDao.getTeacher(teacher).getId());

        assertNull("Teacher exists after remove", iTeacherDao.getTeacher(teacher));
    }

    @Test
    public void checkTotalTeachers() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        assertThat(iTeacherDao.getAllTeachers(0, 10).size(), is(1));
    }

    @Test
    public void updateTeacherIsPossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        updatedTeacher = iTeacherDao.getTeacher(teacher);
        updatedTeacher.setName("Updated teacher");
        updatedTeacher.setExperience(7);

        assertTrue("Teacher didn't update", iTeacherDao.updateTeacher(updatedTeacher));
    }

    @Test
    public void teacherUpdated() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        updatedTeacher = iTeacherDao.getTeacher(teacher);
        updatedTeacher.setName("Updated teacher");
        updatedTeacher.setExperience(7);

        iTeacherDao.updateTeacher(updatedTeacher);

        assertEquals("Teacher didn't update", updatedTeacher, iTeacherDao.getTeacher(updatedTeacher));
    }

    @Test
    public void checkGetTeachersByExperience() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        assertThat(iTeacherDao.getTeacherByExperience(3, 0, 10).size(), is(1));
    }

    @Test
    public void checkGetMaxExperienced() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        assertThat(iTeacherDao.getMaxExperiencedTeachers(0, 10).size(), is(1));
    }

    @Test
    public void checkGetMinExperienced() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        assertThat(iTeacherDao.getMinExperiencedTeachers(0, 10).size(), is(1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void canNotRemoveIfTeacherNotExists() throws EntityNotFoundException {
        iTeacherDao.removeTeacher(teacher.getId());
    }
}
