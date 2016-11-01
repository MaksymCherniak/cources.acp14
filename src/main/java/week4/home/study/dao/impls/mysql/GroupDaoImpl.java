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

import java.util.ArrayList;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class GroupDaoImpl implements IGroupDao {
    private static Logger log = Logger.getLogger(GroupDaoImpl.class.getName());
    @Autowired
    private GroupRepository groupRepository;

    public GroupDaoImpl() {
    }

    @Override
    public boolean addGroup(Groups groups) throws ComingNullObjectException, EntityAlreadyExistException {

        if (groups == null || groups.getName() == null) {
            throw new ComingNullObjectException(Groups.class.getName(), OPERATION_UPDATE);
        }

        if (groupRepository.getGroupByName(groups.getName()) != null) {
            throw new EntityAlreadyExistException(groups);
        }

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
    public boolean updateAll(Groups groups) throws ComingNullObjectException {

        if (groups == null || groups.getName() == null) {
            throw new ComingNullObjectException(Groups.class.getName(), OPERATION_UPDATE);
        }

        groupRepository.saveAndFlush(groups);

        log.info(groups.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    @Override
    public boolean updateGroup(Groups groups) throws ComingNullObjectException, EntityAlreadyExistException {

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

    @Override
    public Groups getGroup(Groups groups) throws EntityNotFoundException {
        Groups result = groupRepository.getGroupByName(groups.getName());
        if (result != null) {
            return result;
        }

        log.info(ERROR_GROUP_NOT_FOUND);
        throw new EntityNotFoundException(Groups.class.getSimpleName());
    }

    @Override
    public Groups getGroupById(long id) {
        return groupRepository.findOne(id);
    }

    @Override
    public List<Groups> getAllGroups(int from, int quantity) throws EntityNotFoundException {
        return getAllGroups(ALL, null, from, quantity);
    }

    @Override
    public List<Groups> getGroupsLike(String name, int from, int quantity) throws EntityNotFoundException {
        return getAllGroups(NAME, name, from, quantity);
    }

    @Override
    public List<Groups> getGroupsBySubject(String subject, int from, int quantity) throws EntityNotFoundException {
        return getAllGroups(SUBJECT, subject, from, quantity);
    }

    /**
     * @param operation(String) - ALL - for getAllGroups() method
     *                          - NAME - for getGroupsLike() method
     *                          - SUBJECT - for getGroupsBySubject() method
     * @param param(String)     - name - group name, for NAME operation
     *                          - subject - subject name, for SUBJECT operation
     * @param from(int)         the starting row of entries returning
     * @param quantity(int)     the total number of entries returning
     * @return List of groups
     * @throws EntityNotFoundException
     */
    private List<Groups> getAllGroups(String operation, String param, int from, int quantity) throws EntityNotFoundException {
        List<Groups> groupsList = new ArrayList<>();
        switch (operation) {
            case ALL:
                groupsList = groupRepository.findAll(new PageRequest(from, quantity)).getContent();
                break;
            case NAME:
                groupsList = groupRepository.getGroupsLike(param, new PageRequest(from, quantity));
                break;
            case SUBJECT:
                groupsList = groupRepository.getGroupsBySubject(param, new PageRequest(from, quantity));
                break;
        }

        if (groupsList.size() != 0) {
            return groupsList;
        }

        log.info(ERROR_GROUP_NOT_FOUND);
        throw new EntityNotFoundException(Groups.class.getSimpleName());
    }
}
