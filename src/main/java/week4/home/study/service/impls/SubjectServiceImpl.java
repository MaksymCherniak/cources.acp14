package week4.home.study.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.service.interfaces.ISubjectService;

import java.util.List;

import static week4.home.study.start.AppStaticValues.DEFAULT_QUANTITY_VALUE;

@Service
public class SubjectServiceImpl implements ISubjectService {
    @Autowired
    private ISubjectDao iSubjectDao;
    @Autowired
    private IGroupDao iGroupDao;
    @Autowired
    private ITeacherDao iTeacherDao;

    @Override
    public boolean addSubject(String name, String description) throws EntityAlreadyExistException
            , ComingNullObjectException {

        return iSubjectDao.addSubject(new Subject(name, description));
    }

    @Override
    public boolean removeSubject(String name) throws EntityNotFoundException {
        return iSubjectDao.removeSubject(iSubjectDao.getSubjectByName(name).getId());
    }

    @Override
    public boolean updateSubject(String oldName, String newName, String description) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException {

        Subject subject = iSubjectDao.getSubjectByName(oldName);

        if (newName != null && !newName.equals("")) {
            subject.setName(newName);
        }

        if (description != null && !description.equals(subject.getDescription())) {
            subject.setDescription(description);
        }

        return iSubjectDao.updateSubject(subject);
    }

    @Override
    public Subject getSubjectByName(String name) throws EntityNotFoundException {
        return iSubjectDao.getSubjectByName(name);
    }

    @Override
    public Subject getSubjectByTeacher(String teacherName) throws EntityNotFoundException {
        Teacher teacher = iTeacherDao.getTeacherByName(teacherName);

        return teacher.getSubject();
    }

    @Override
    public List<Subject> getAllSubjects(int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iSubjectDao.getAllSubjects(from, quantity);
    }

    @Override
    public List<Subject> getAllSubjectsByGroupName(String groupName, int from, int quantity) throws EntityNotFoundException {
        Groups groups = iGroupDao.getGroup(new Groups(groupName));

        return groups.getSubjects();
    }

    @Override
    public List<Subject> getAllSubjectsByNameLike(String name, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iSubjectDao.getAllSubjectsByNameLike("%" + name + "%", from, quantity);
    }
}
