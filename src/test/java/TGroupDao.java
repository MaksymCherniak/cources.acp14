import org.apache.log4j.Logger;
import org.junit.*;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.impls.mysql.GroupDaoImpl;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.entity.Groups;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TGroupDao {
    private static Logger log = Logger.getLogger(TGroupDao.class.getName());
    private static IGroupDao iGroupDao;

    private Groups groups;

    private static EntityManager entityManager;

    @BeforeClass
    public static void initializeConnection() {
        entityManager = HibernateUtil.getTestEm();
        iGroupDao = new GroupDaoImpl(entityManager);
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
        try{
            iGroupDao.removeGroup(groups.getId());
        } catch (EntityNotFoundException e) {}
    }

    @Test
    public void addGroupIsPossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        assertTrue("Group didn't add", iGroupDao.addGroup(groups));
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void addDuplicateGroupIsImpossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iGroupDao.addGroup(groups);

        iGroupDao.addGroup(groups);
    }

    @Test
    public void groupExistsAfterAdd() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {
        iGroupDao.addGroup(groups);

        assertNotNull("Group not found", iGroupDao.getGroup(groups));
    }

    @Test(expected = EntityNotFoundException.class)
    public void groupDoesNotExistAfterRemove() throws ComingNullObjectException, OperationFailedException
            , EntityNotFoundException, EntityAlreadyExistException {

        iGroupDao.addGroup(groups);

        iGroupDao.removeGroup(iGroupDao.getGroup(groups).getId());

        assertNull("Group exists after remove", iGroupDao.getGroup(groups));
    }

    @Test
    public void checkTotalGroups() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        iGroupDao.addGroup(groups);

        assertThat(iGroupDao.getAllGroups(0, 10).size(), is(1));
    }

    @Test
    public void updateGroupIsPossible() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {
        iGroupDao.addGroup(groups);

        Groups newGroup = iGroupDao.getGroup(groups);
        newGroup.setName("Updated");

        assertTrue("Group didn't update", iGroupDao.updateGroup(newGroup));
    }

    @Test
    public void groupUpdated() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {
        iGroupDao.addGroup(groups);

        Groups newGroup = iGroupDao.getGroup(groups);
        newGroup.setName("Updated");

        iGroupDao.updateGroup(newGroup);

        assertEquals("Group didn't update", newGroup, iGroupDao.getGroup(newGroup));
    }

    @Test(expected = EntityNotFoundException.class)
    public void canNotRemoveIfGroupNotExists() throws EntityNotFoundException {
        iGroupDao.removeGroup(groups.getId());
    }
}
