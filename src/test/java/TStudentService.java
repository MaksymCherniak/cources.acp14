import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import week4.home.jdbc.dao.JDBCDriver;
import week4.home.jdbc.dao.services.IGroupService;
import week4.home.jdbc.dao.services.IStudentService;
import week4.home.jdbc.dao.services.ServiceFactory;
import week4.home.jdbc.entity.Group;
import week4.home.jdbc.entity.Student;
import week4.home.jdbc.main.AppStaticValues;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertNotNull;

public class TStudentService {
    private static Logger log = Logger.getLogger(TStudentService.class.getName());
    private IStudentService iStudentService = ServiceFactory.getStudentInstance();
    private IGroupService iGroupService = ServiceFactory.getGroupInstance();
    private Student student;
    private Group group;

    private static Connection connection;
    private static ScriptRunner scriptRunner;

    static {
        connection = JDBCDriver.getTestConnection();
        scriptRunner = new ScriptRunner(connection);
    }

    @BeforeClass
    public static void initializeConnection() {
        scriptRunner.runScript(new InputStreamReader(Student.class.getResourceAsStream("/sql/createSchema")));
    }

    @AfterClass
    public static void shutdownConnection() {
        scriptRunner.runScript(new InputStreamReader(Student.class.getResourceAsStream("/sql/dropTables")));

        try {
            connection.close();
        } catch (SQLException e) {
            log.warn(AppStaticValues.ERROR_CONNECTION_CLOSE);
        }
    }

    @After
    public void after() {
        scriptRunner.runScript(new InputStreamReader(Student.class.getResourceAsStream("/sql/truncateTables")));
    }

    @Before
    public void initialize() {

        group = new Group();
        group.setId(994);
        group.setName("Test group");

        student = new Student();
        student.setId(663);
        student.setName("Test name");
        student.setGroup(group.getId());

    }

    @Test
    public void whenAddTheStudentExists() {
       // assertNull("Student exists before added", iStudentService.getStudentById(student.getId()));

        iGroupService.addGroup(group);
       iStudentService.addStudent(student);

        assertNotNull("Student does not exist after added", iStudentService.getStudentById(student.getId()));

       // iStudentService.removeStudent(student.getId());

       // assertNull("Student exists after removed", iStudentService.getStudentById(student.getId()));
    }
}
