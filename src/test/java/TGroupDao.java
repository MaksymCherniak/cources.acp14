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
import week4.home.study.entity.Groups;
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
public class TGroupDao {
    private static Logger log = Logger.getLogger(TGroupDao.class.getName());
    @Autowired
    private IGroupDao iGroupDao;

    private Groups groups;
    private Groups updated;

    @Before
    public void initialize() throws OperationFailedException, EntityAlreadyExistException, ComingNullObjectException {
        groups = new Groups();
        groups.setName("Test group name");
    }

    @After
    public void delete() {
        try {
            iGroupDao.removeGroup(iGroupDao.getGroup(groups).getId());
        } catch (EntityNotFoundException e) {
        }
    }

    @Test
    public void addGroupIsPossible() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        assertTrue("Group didn't add", iGroupDao.addGroup(new Groups("group")));
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
    public void checkTotalGroups() throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException
            , EntityNotFoundException {

        iGroupDao.addGroup(groups);

        assertThat(iGroupDao.getAllGroups(0, 10).size(), is(1));
    }

    @Test
    public void updateGroupIsPossible() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        iGroupDao.addGroup(groups);

        updated = iGroupDao.getGroup(groups);
        updated.setName("Updated");

        assertNotNull("Group didn't update", iGroupDao.getGroup(updated));

        iGroupDao.removeGroup(iGroupDao.getGroup(updated).getId());
    }

    @Test
    public void groupUpdated() throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException, EntityNotFoundException {

        iGroupDao.addGroup(groups);

        updated = iGroupDao.getGroup(groups);
        updated.setName("Updated");

        assertEquals("Group didn't update", updated, iGroupDao.getGroup(updated));

        iGroupDao.removeGroup(iGroupDao.getGroup(updated).getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void canNotRemoveIfGroupNotExists() throws EntityNotFoundException {
        iGroupDao.removeGroup(iGroupDao.getGroup(groups).getId());
    }
}
