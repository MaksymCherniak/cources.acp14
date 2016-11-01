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
import week4.home.study.service.interfaces.IGroupService;
import week4.home.study.start.AppStaticValues;

import java.util.List;

import static week4.home.study.start.AppStaticValues.DEFAULT_QUANTITY_VALUE;

@Service
public class GroupServiceImpl implements IGroupService {
    @Autowired
    private IGroupDao iGroupDao;
    @Autowired
    private IStudentDao iStudentDao;
    @Autowired
    private ISubjectDao iSubjectDao;

    @Override
    public boolean addGroup(String name) throws EntityAlreadyExistException, ComingNullObjectException {
        return iGroupDao.addGroup(new Groups(name));
    }

    @Override
    public boolean removeGroup(String name) throws EntityNotFoundException {
        return iGroupDao.removeGroup(iGroupDao.getGroup(new Groups(name)).getId());
    }

    @Override
    public boolean updateGroup(String oldName, String newName) throws EntityNotFoundException, EntityAlreadyExistException
            , ComingNullObjectException {

        Groups groups = iGroupDao.getGroup(new Groups(oldName));
        groups.setName(newName);

        return iGroupDao.updateGroup(groups);
    }

    @Override
    public Groups getGroupByName(String name) throws EntityNotFoundException {
        return iGroupDao.getGroup(new Groups(name));
    }

    @Override
    public Groups getGroupByStudentName(String name) throws EntityNotFoundException {
        Student student = iStudentDao.getStudentByName(name);

        return student.getGroups();
    }

    @Override
    public List<Groups> getAllGroups(int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iGroupDao.getAllGroups(from, quantity);
    }

    @Override
    public List<Groups> getGroupsLike(String name, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iGroupDao.getGroupsLike("%" + name + "%", from, quantity);
    }

    @Override
    public List<Groups> getGroupsBySubject(String subjectName, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iGroupDao.getGroupsBySubject(subjectName, from, quantity);
    }

    @Override
    public boolean setSubject(String groupName, String subjectName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException {

        Groups groups = iGroupDao.getGroup(new Groups(groupName));
        Subject subject = iSubjectDao.getSubjectByName(subjectName);

        List<Subject> subjects = groups.getSubjects();

        if (subjects.contains(subject)) {
            throw new EntityAlreadyExistException(groups, subject);
        }

        subjects.add(subject);
        groups.setSubjects(subjects);

        return iGroupDao.updateAll(groups);
    }
}
