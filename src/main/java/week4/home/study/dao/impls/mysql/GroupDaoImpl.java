package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.dao.repositories.GroupRepository;
import week4.home.study.entity.Groups;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class GroupDaoImpl implements IGroupDao {
    private static Logger log = Logger.getLogger(GroupDaoImpl.class.getName());
    @Autowired
    private GroupRepository groupRepository;

    public GroupDaoImpl() {
    }

    public boolean addGroup(Groups groups) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        if (groups == null || groups.getName() == null) {
            throw new ComingNullObjectException(Groups.class.getName(), OPERATION_UPDATE);
        }

        if (groupRepository.getGroupByName(groups.getName()) != null) {
            throw new EntityAlreadyExistException(groups);
        }

        if (groupRepository.saveAndFlush(groups) != null) {
            log.info(groups.toString() + LOG_OPERATION_ADD);
            return true;
        }

        throw new OperationFailedException(Groups.class.getName(), OPERATION_ADD);
    }

    public boolean removeGroup(long id) {
        Groups groups = getGroupById(id);

        groupRepository.delete(groups);

        log.info(groups.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    public boolean updateGroup(Groups groups) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        if (groups == null || groups.getName() == null) {
            throw new ComingNullObjectException(Groups.class.getName(), OPERATION_UPDATE);
        }

        if (groupRepository.getGroupByName(groups.getName()) != null) {
            throw new EntityAlreadyExistException(groups);
        }

        groupRepository.updateGroup(groups.getName(), groups.getId());
        log.info(groups.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    public Groups getGroup(Groups groups) throws EntityNotFoundException {
        Groups result = groupRepository.getGroupByName(groups.getName());
        if (result != null) {
            return result;
        }

        log.info(ERROR_GROUP_NOT_FOUND);
        throw new EntityNotFoundException(Groups.class.getSimpleName());
    }

    public Groups getGroupById(long id) {
        return groupRepository.findOne(id);
    }

    public List<Groups> getAllGroups(int from, int quantity) throws EntityNotFoundException {
        List<Groups> groupsList = groupRepository.findAll(new PageRequest(from, quantity)).getContent();
        log.info(groupsList.size());

        if (groupsList.size() != 0) {
            return groupsList;
        }

        log.info(ERROR_GROUP_NOT_FOUND);
        throw new EntityNotFoundException(Groups.class.getSimpleName());
    }
}
