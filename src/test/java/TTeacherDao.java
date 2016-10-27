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
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;
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
public class TTeacherDao {
    private static Logger log = Logger.getLogger(TTeacherDao.class.getName());
    @Autowired
    private ITeacherDao iTeacherDao;
    @Autowired
    private ISubjectDao iSubjectDao;

    private Subject subject;
    private Teacher teacher;
    private Teacher updatedTeacher;

    @Before
    public void initialize() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        teacher = new Teacher();
        subject = new Subject();
        subject.setName("Test subject");
        subject.setDescription("Test description");

        iSubjectDao.addSubject(subject);

        teacher.setName("Test teacher");
        teacher.setExperience(5);
        teacher.setSubject(iSubjectDao.getSubject(subject));
    }

    @After
    public void delete() throws EntityNotFoundException {
        try {
            iTeacherDao.removeTeacher(iTeacherDao.getTeacher(teacher).getId());
        } catch (EntityNotFoundException e) {
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
    public void teacherExistsAfterAdd() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        iTeacherDao.addTeacher(teacher);

        assertNotNull("Teacher not found", iTeacherDao.getTeacher(teacher));
    }

    @Test(expected = EntityNotFoundException.class)
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
    public void updateTeacherIsPossible() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        iTeacherDao.addTeacher(teacher);

        updatedTeacher = iTeacherDao.getTeacher(teacher);
        updatedTeacher.setName("Updated teacher");
        updatedTeacher.setExperience(7);

        assertNotNull("Teacher didn't update", iTeacherDao.getTeacher(updatedTeacher));
    }

    @Test
    public void teacherUpdated() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        iTeacherDao.addTeacher(teacher);

        updatedTeacher = iTeacherDao.getTeacher(teacher);
        updatedTeacher.setName("Updated teacher");
        updatedTeacher.setExperience(7);

        assertEquals("Teacher didn't update", updatedTeacher, iTeacherDao.getTeacher(updatedTeacher));
    }

    @Test
    public void checkGetTeachersByExperience() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iTeacherDao.addTeacher(teacher);

        assertThat(iTeacherDao.getTeacherByExperience(3, 0, 10).size(), is(1));
    }

    @Test
    public void checkGetMaxExperienced() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException, EntityNotFoundException {
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
        iTeacherDao.removeTeacher(iTeacherDao.getTeacher(teacher).getId());
    }
}
