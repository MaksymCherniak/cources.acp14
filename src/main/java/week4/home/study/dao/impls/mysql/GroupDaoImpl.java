package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.dao.repositories.GroupRepository;
import week4.home.study.entity.Groups;

import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

public class GroupDaoImpl implements IGroupDao {
    private static Logger log = Logger.getLogger(GroupDaoImpl.class.getName());
    @Autowired
    private GroupRepository groupRepository;

    public GroupDaoImpl() {
    }

    @Override
    public boolean addGroup(Groups groups) {

        groupRepository.save(groups);

        log.info(groups.toString() + LOG_OPERATION_ADD);
        return true;
    }

    @Override
    public boolean removeGroup(long id) {
        Groups groups = getGroupById(id);

        groupRepository.delete(groups);

        log.info(groups.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    @Override
    public boolean updateAll(Groups groups) {

        groupRepository.saveAndFlush(groups);

        log.info(groups.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    @Override
    public boolean updateGroup(Groups groups) {

        groupRepository.updateGroup(groups.getName(), groups.getId());

        log.info(groups.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    @Override
    public Groups getGroup(Groups groups) {
        return groupRepository.getGroupByName(groups.getName());
    }

    @Override
    public Groups getGroupById(long id) {
        return groupRepository.findOne(id);
    }

    @Override
    public List<Groups> getAllGroups(int from, int quantity) {
        return groupRepository.findAll(new PageRequest(from, quantity)).getContent();
    }

    @Override
    public List<Groups> getGroupsLike(String name, int from, int quantity) {
        return groupRepository.getGroupsLike(name, new PageRequest(from, quantity));
    }

    @Override
    public List<Groups> getGroupsBySubject(String subject, int from, int quantity) {
        return groupRepository.getGroupsBySubject(subject, new PageRequest(from, quantity));
    }
}
