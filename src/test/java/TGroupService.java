import org.apache.log4j.Logger;
import org.junit.*;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.impls.mysql.GroupServiceImpl;
import week4.home.study.dao.services.IGroupService;
import week4.home.study.dao.services.ServiceFactory;
import week4.home.study.entity.Groups;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TGroupService {
    private static Logger log = Logger.getLogger(TGroupService.class.getName());
    private static IGroupService iGroupService = ServiceFactory.getGroupInstance();

    private Groups groups;

    private static EntityManager entityManager;

    @BeforeClass
    public static void initializeConnection() {
        entityManager = HibernateUtil.getTestEm();
        GroupServiceImpl.setEntityManager(entityManager);
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
    }

    @After
    public void delete() {
        Groups g = iGroupService.getGroup(groups);

        if (g != null) {
            iGroupService.removeGroup(g.getId());
        }
    }

    @Test
    public void addGroupIsPossible() {
        assertTrue("Group didn't add", iGroupService.addGroup(groups));
    }

    @Test
    public void addDuplicateGroupIsImpossible() {
        iGroupService.addGroup(groups);

        assertFalse("Duplicate group added", iGroupService.addGroup(groups));
    }

    @Test
    public void groupExistsAfterAdd() {
        iGroupService.addGroup(groups);

        assertNotNull("Group not found", iGroupService.getGroup(groups));
    }

    @Test
    public void groupDoesNotExistAfterRemove() {
        iGroupService.addGroup(groups);

        iGroupService.removeGroup(iGroupService.getGroup(groups).getId());

        assertNull("Group exists after remove", iGroupService.getGroup(groups));
    }

    @Test
    public void checkTotalGroups() {
        iGroupService.addGroup(groups);

        assertThat(iGroupService.getAllGroups().size(), is(1));
    }

    @Test
    public void updateGroupIsPossible() {
        iGroupService.addGroup(groups);

        Groups newGroup = iGroupService.getGroup(groups);
        newGroup.setName("Updated");

        assertTrue("Group didn't update", iGroupService.updateGroup(newGroup));
    }

    @Test
    public void groupUpdated() {
        iGroupService.addGroup(groups);

        Groups newGroup = iGroupService.getGroup(groups);
        newGroup.setName("Updated");

        iGroupService.updateGroup(newGroup);

        assertEquals("Group didn't update", newGroup, iGroupService.getGroup(newGroup));
    }
}
