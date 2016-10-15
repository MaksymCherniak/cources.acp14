import org.apache.log4j.Logger;
import org.junit.*;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.impls.mysql.SubjectServiceImpl;
import week4.home.study.dao.impls.mysql.TeacherServiceImpl;
import week4.home.study.dao.services.ISubjectService;
import week4.home.study.dao.services.ITeacherService;
import week4.home.study.dao.services.ServiceFactory;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TTeacherService {
    private static Logger log = Logger.getLogger(TTeacherService.class.getName());
    private static ITeacherService iTeacherService = ServiceFactory.getTeacherInstance();
    private static ISubjectService iSubjectService = ServiceFactory.getSubjectInstance();

    private Subject subject;
    private Teacher teacher;
    private Teacher updatedTeacher;

    private static EntityManager entityManager;

    @BeforeClass
    public static void initializeConnection() {
        entityManager = HibernateUtil.getTestEm();
        SubjectServiceImpl.setEntityManager(entityManager);
        TeacherServiceImpl.setEntityManager(entityManager);
    }

    @AfterClass
    public static void shutdownConnection() {
        entityManager.clear();
        entityManager.close();
    }

    @Before
    public void initialize() {
        subject = new Subject();
        subject.setName("Test subject");
        subject.setDescription("Test description");

        iSubjectService.addSubject(subject);

        teacher = new Teacher();
        teacher.setName("Test teacher");
        teacher.setExperience(5);
        teacher.setSubject(iSubjectService.getSubject(subject));
    }

    @After
    public void delete() {
        Teacher teach = iTeacherService.getTeacher(teacher);

        if (teach != null) {
            iTeacherService.removeTeacher(teach.getId());
        }
    }

    @Test
    public void addTeacherIsPossible() {
        assertTrue("Teacher didn't add", iTeacherService.addTeacher(teacher));
    }

    @Test
    public void addDuplicateTeacherIsImpossible() {
        iTeacherService.addTeacher(teacher);

        assertFalse("Duplicate teacher added", iTeacherService.addTeacher(teacher));
    }

    @Test
    public void teacherExistsAfterAdd() {
        iTeacherService.addTeacher(teacher);

        assertNotNull("Teacher not found", iTeacherService.getTeacher(teacher));
    }

    @Test
    public void teacherDoesNotExistAfterRemove() {
        iTeacherService.addTeacher(teacher);

        iTeacherService.removeTeacher(iTeacherService.getTeacher(teacher).getId());

        assertNull("Teacher exists after remove", iTeacherService.getTeacher(teacher));
    }

    @Test
    public void checkTotalTeachers() {
        iTeacherService.addTeacher(teacher);

        assertThat(iTeacherService.getAllTeachers().size(), is(1));
    }

    @Test
    public void updateTeacherIsPossible() {
        iTeacherService.addTeacher(teacher);

        updatedTeacher = iTeacherService.getTeacher(teacher);
        updatedTeacher.setName("Updated teacher");
        updatedTeacher.setExperience(7);

        assertTrue("Teacher didn't update", iTeacherService.updateTeacher(updatedTeacher));
    }

    @Test
    public void teacherUpdated() {
        iTeacherService.addTeacher(teacher);

        updatedTeacher = iTeacherService.getTeacher(teacher);
        updatedTeacher.setName("Updated teacher");
        updatedTeacher.setExperience(7);

        iTeacherService.updateTeacher(updatedTeacher);

        assertEquals("Teacher didn't update", updatedTeacher, iTeacherService.getTeacher(updatedTeacher));
    }

    @Test
    public void checkGetTeachersByExperience() {
        iTeacherService.addTeacher(teacher);

        assertThat(iTeacherService.getTeacherByExperience().size(), is(1));
    }

    @Test
    public void checkGetMaxExperienced() {
        iTeacherService.addTeacher(teacher);

        assertThat(iTeacherService.getMaxExperienced().size(), is(1));
    }

    @Test
    public void checkGetMinExperienced() {
        iTeacherService.addTeacher(teacher);

        assertThat(iTeacherService.getMinExperienced().size(), is(1));
    }
}
