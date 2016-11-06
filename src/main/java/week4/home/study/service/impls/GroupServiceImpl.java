package week4.home.study.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.InvalidNameFormatException;
import week4.home.study.service.interfaces.IGroupService;
import week4.home.study.main.Validator;

import java.util.ArrayList;
import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

@Service
public class GroupServiceImpl implements IGroupService {
    @Autowired
    private IGroupDao iGroupDao;
    @Autowired
    private IStudentDao iStudentDao;
    @Autowired
    private ISubjectDao iSubjectDao;

    @Override
    public boolean addGroup(String name) throws EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException {

        Validator.groupName(name);

        Groups groups = new Groups(name);

        Validator.checkAlreadyExist(iGroupDao.getGroup(groups));

        return iGroupDao.addGroup(groups);
    }

    @Override
    public boolean removeGroup(String name) throws EntityNotFoundException {

        Groups groups = iGroupDao.getGroup(new Groups(name));

        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        return iGroupDao.removeGroup(groups.getId());
    }

    @Override
    public boolean updateGroup(String oldName, String newName) throws EntityNotFoundException, EntityAlreadyExistException
            , ComingNullObjectException, InvalidNameFormatException {

        Validator.groupName(oldName);
        Validator.groupName(newName);

        Groups groups = iGroupDao.getGroup(new Groups(oldName));

        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        Validator.checkAlreadyExist(iGroupDao.getGroup(new Groups(newName)));

        groups.setName(newName);

        return iGroupDao.updateGroup(groups);
    }

    @Override
    public Groups getGroupByName(String name) throws EntityNotFoundException, ComingNullObjectException, InvalidNameFormatException {

        Validator.groupName(name);

        Groups groups = iGroupDao.getGroup(new Groups(name));

        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        return groups;
    }

    @Override
    public Groups getGroupByStudentName(String name) throws EntityNotFoundException {
        Student student = iStudentDao.getStudentByName(name);

        Validator.checkNullObject(Student.class.getSimpleName(), student);

        return student.getGroups();
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
    public List<Groups> getGroupsBySubject(String subjectName, int from, int quantity) throws EntityNotFoundException {
        return getAllGroups(SUBJECT, subjectName, from, quantity);
    }

    @Override
    public boolean setSubject(String groupName, String subjectName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException {

        Validator.groupName(groupName);
        Validator.subjectName(subjectName);

        Groups groups = iGroupDao.getGroup(new Groups(groupName));
        Subject subject = iSubjectDao.getSubjectByName(subjectName);

        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        Validator.checkNullObject(Subject.class.getSimpleName(), subject);

        List<Subject> subjects = groups.getSubjects();

        if (subjects.contains(subject)) {
            throw new EntityAlreadyExistException(groups, subject);
        }

        subjects.add(subject);
        groups.setSubjects(subjects);

        return iGroupDao.updateAll(groups);
    }

    /**
     * @param operation(String) - ALL - for getAllGroups() method
     *                          - NAME - for getGroupsLike() method
     *                          - SUBJECT - for getGroupsBySubject() method
     * @param param(String)     - name - group name, for NAME operation
     *                          - subject - subject name, for SUBJECT operation
     * @param from(int)         the starting row of entries returning
     * @param quantity(int)     the total number of entries returning
     * @return List of group
     * @throws EntityNotFoundException
     */
    private List<Groups> getAllGroups(String operation, String param, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        List<Groups> groupsList = new ArrayList<>();
        switch (operation) {
            case ALL:
                groupsList = iGroupDao.getAllGroups(from, quantity);
                break;
            case NAME:
                groupsList = iGroupDao.getGroupsLike(param, from, quantity);
                break;
            case SUBJECT:
                groupsList = iGroupDao.getGroupsBySubject(param, from, quantity);
                break;
        }

        if (groupsList.size() != 0) {
            return groupsList;
        }

        throw new EntityNotFoundException(Groups.class.getSimpleName());
    }
}
