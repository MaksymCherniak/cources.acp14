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
import week4.home.study.exceptions.InvalidNameFormatException;
import week4.home.study.main.Validator;
import week4.home.study.service.interfaces.ISubjectService;

import java.util.ArrayList;
import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

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
            , ComingNullObjectException, InvalidNameFormatException, EntityNotFoundException {

        Validator.subjectName(name);

        Validator.checkNullObject(Subject.class.getSimpleName(), iSubjectDao.getSubjectByName(name));

        return iSubjectDao.addSubject(new Subject(name, description));
    }

    @Override
    public boolean removeSubject(String name) throws EntityNotFoundException {

        Subject subject = iSubjectDao.getSubjectByName(name);

        Validator.checkNullObject(Subject.class.getSimpleName(), subject);

        return iSubjectDao.removeSubject(subject.getId());
    }

    @Override
    public boolean updateSubject(String oldName, String newName, String description) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException {

        Validator.subjectName(oldName);
        Validator.subjectName(newName);

        Subject subject = iSubjectDao.getSubjectByName(oldName);

        Validator.checkNullObject(Subject.class.getSimpleName(), subject);

        if (!oldName.equals(newName)) {
            Validator.checkAlreadyExist(iSubjectDao.getSubjectByName(newName));
        }

        subject.setName(newName);

        if (description != null && !description.equals(subject.getDescription())) {
            subject.setDescription(description);
        }

        return iSubjectDao.updateSubject(subject);
    }

    @Override
    public Subject getSubjectByName(String name) throws EntityNotFoundException {
        return getSingleResult(NAME, name);
    }

    @Override
    public Subject getSubjectByTeacher(String teacherName) throws EntityNotFoundException {
        Teacher teacher = iTeacherDao.getTeacherByName(teacherName);

        Validator.checkNullObject(Teacher.class.getSimpleName(), teacher);

        return teacher.getSubject();
    }

    @Override
    public List<Subject> getAllSubjects(int from, int quantity) throws EntityNotFoundException {
        return getAllSubjects(ALL, null, from, quantity);
    }

    @Override
    public List<Subject> getAllSubjectsByGroupName(String groupName, int from, int quantity) throws EntityNotFoundException {
        Groups groups = iGroupDao.getGroup(new Groups(groupName));

        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        return groups.getSubjects();
    }

    @Override
    public List<Subject> getAllSubjectsByNameLike(String name, int from, int quantity) throws EntityNotFoundException {
        return getAllSubjects(NAME, name, from, quantity);
    }

    /**
     * @param operation(String) - ALL - for getSubject() method
     *                          - NAME - for getSubjectByName() method
     * @param param(String)     - name - subject name, for ALL and NAME operations
     *                          - description - subject description, for ALL operation
     * @return Subject entity
     * @throws EntityNotFoundException
     */
    private Subject getSingleResult(String operation, String... param) throws EntityNotFoundException {
        Subject result = null;

        switch (operation) {
            case ALL:
                result = iSubjectDao.getSubject(new Subject(param[0], param[1]));
                break;
            case NAME:
                result = iSubjectDao.getSubjectByName(param[0]);
                break;
        }

        Validator.checkNullObject(Subject.class.getSimpleName(), result);

        return result;
    }

    /**
     * @param operation(String) - ALL - for getAllSubjects() method
     *                          - NAME - for getAllSubjectsByNameLike() method
     * @param param(String)     - name - subject name, for NAME operation
     * @param from(int)         the starting row of entries returning
     * @param quantity(int)     the total number of entries returning
     * @return List of students
     * @throws EntityNotFoundException
     */
    private List<Subject> getAllSubjects(String operation, String param, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        List<Subject> result = new ArrayList<>();

        switch (operation) {
            case ALL:
                result = iSubjectDao.getAllSubjects(from, quantity);
                break;
            case NAME:
                result = iSubjectDao.getAllSubjectsByNameLike(param, from, quantity);
                break;
        }

        if (result.size() != 0) {
            return result;
        }

        throw new EntityNotFoundException(Subject.class.getName());
    }
}
