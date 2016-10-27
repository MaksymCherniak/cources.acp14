import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import week4.home.study.dao.repositories.GroupRepository;
import week4.home.study.entity.Groups;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"jdbc.url=jdbc:mysql://localhost:3306/studiestest", "hibernate.hbm2ddl.auto=create-drop"})
@ContextConfiguration("classpath:spring/testApplicationConfig.xml")
public class TGroupRepository {
    private Groups groups;
    private Groups updated;
    @Autowired
    private GroupRepository groupRepository;

    @Before
    public void initialize() {
        groups = new Groups();
        groups.setName("testGroup");
    }

    @After
    public void delete() {
        if (groupRepository.getGroupByName(groups.getName()) != null) {
            groupRepository.delete(groupRepository.getGroupByName(groups.getName()).getId());
        }
    }

    @Test
    public void noGroupEntriesFoundIfNotInDatabase() {
        assertNull("Group which isn't in database exists", groupRepository.getGroupByName("newName"));
    }

    @Test
    public void groupEntryFoundIfInDatabase() {
        groupRepository.save(groups);

        assertNotNull("Group not found", groupRepository.getGroupByName(groups.getName()));
    }

    @Test
    public void groupEntryNotFoundAfterRemoved() {
        groupRepository.save(groups);

        groupRepository.delete(groupRepository.getGroupByName(groups.getName()).getId());

        assertNull("Group which removed from database exists", groupRepository.getGroupByName(groups.getName()));

    }

    @Test
    public void updatedGroupInDatabase() {
        groupRepository.save(groups);

        updated = groupRepository.getGroupByName(groups.getName());
        updated.setName("updated name");

        groupRepository.updateGroup(updated.getName(), updated.getId());

        assertNotNull("Group not found", groupRepository.getGroupByName(updated.getName()));

        groupRepository.delete(groupRepository.getGroupByName(updated.getName()).getId());
    }

    @Test
    public void oldGroupNotFoundIfUpdated() {
        groupRepository.save(groups);

        updated = groupRepository.getGroupByName(groups.getName());
        updated.setName("updated name");

        groupRepository.updateGroup(updated.getName(), updated.getId());

        assertNull("Old group exists", groupRepository.getGroupByName(groups.getName()));

        groupRepository.delete(groupRepository.getGroupByName(updated.getName()).getId());
    }
}
