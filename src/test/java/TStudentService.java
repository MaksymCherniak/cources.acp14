import org.apache.log4j.Logger;
import org.junit.*;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.impls.mysql.GroupServiceImpl;
import week4.home.study.dao.impls.mysql.StudentServiceImpl;
import week4.home.study.dao.services.IGroupService;
import week4.home.study.dao.services.IStudentService;
import week4.home.study.dao.services.ServiceFactory;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TStudentService {
    private static Logger log = Logger.getLogger(TStudentService.class.getName());
    private static IStudentService iStudentService = ServiceFactory.getStudentInstance();
    private static IGroupService iGroupService = ServiceFactory.getGroupInstance();

    private Student student;
    private Groups groups;

    private static EntityManager entityManager;

    @BeforeClass
    public static void initializeConnection() {
        entityManager = HibernateUtil.getTestEm();
        GroupServiceImpl.setEntityManager(entityManager);
        StudentServiceImpl.setEntityManager(entityManager);
    }

    @AfterClass
    public static void shutdownConnection() {
        entityManager.clear();
        entityManager.close();
    }

    @Before
    public void initialize() {
        groups = new Groups();
        groups.setName("Test group");

        iGroupService.addGroup(groups);

        student = new Student();
        student.setName("Test student");
        student.setGroups(iGroupService.getGroup(groups));
    }

    @After
    public void delete() {
        Student st = iStudentService.getStudent(student);

        if (st != null) {
            iStudentService.removeStudent(st.getId());
        }
    }

    @Test
    public void addStudentIsPossible() {
        assertTrue("Student didn't add", iStudentService.addStudent(student));
    }

    @Test
    public void addDuplicateStudentIsImpossible() {
        iStudentService.addStudent(student);

        assertFalse("Duplicate student added", iStudentService.addStudent(student));
    }

    @Test
    public void studentExistsAfterAdd() {
        iStudentService.addStudent(student);

        assertNotNull("Student not found", iStudentService.getStudent(student));
    }

    @Test
    public void studentDoesNotExistAfterRemove() {
        iStudentService.addStudent(student);

        iStudentService.removeStudent(iStudentService.getStudent(student).getId());

        assertNull("Student exists after remove", iStudentService.getStudent(student));
    }

    @Test
    public void checkTotalStudents() {
        iStudentService.addStudent(student);

        assertThat(iStudentService.getAllStudents().size(), is(1));
    }

    @Test
    public void updateStudentIsPossible() {
        iStudentService.addStudent(student);

        Student newStudent = iStudentService.getStudent(student);
        newStudent.setName("Updated student");

        assertTrue("Student didn't update", iStudentService.updateStudent(newStudent));
    }

    @Test
    public void studentUpdated() {
        iStudentService.addStudent(student);

        Student newStudent = iStudentService.getStudent(student);
        newStudent.setName("Updated student");

        iStudentService.updateStudent(newStudent);

        assertEquals("Student didn't update", newStudent, iStudentService.getStudent(newStudent));
    }

    @Test
    public void checkGetStudentsByGroup() {
        iStudentService.addStudent(student);

        assertThat(iStudentService.getStudentsByGroup(iGroupService.getGroup(groups)).size(), is(1));
    }
}
