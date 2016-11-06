package week4.home.study.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.InvalidNameFormatException;
import week4.home.study.main.Validator;
import week4.home.study.service.interfaces.ITeacherService;

import java.util.ArrayList;
import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

@Service
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    private ITeacherDao iTeacherDao;
    @Autowired
    private ISubjectDao iSubjectDao;

    @Override
    public boolean addTeacher(String name, int experience) throws EntityAlreadyExistException
            , ComingNullObjectException, InvalidNameFormatException, EntityNotFoundException {

        Validator.teacherName(name);

        Validator.checkNullObject(Teacher.class.getSimpleName(), iTeacherDao.getTeacherByName(name));

        return iTeacherDao.addTeacher(new Teacher(name, experience));
    }

    @Override
    public boolean removeTeacher(String name) throws EntityNotFoundException {
        Teacher teacher = iTeacherDao.getTeacherByName(name);

        Validator.checkNullObject(Teacher.class.getSimpleName(), teacher);

        return iTeacherDao.removeTeacher(teacher.getId());
    }

    @Override
    public boolean updateTeacher(String oldName, String newName, int experience, String subjectName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException {

        Validator.teacherName(oldName);
        Validator.teacherName(newName);
        Validator.subjectName(subjectName);

        Teacher teacher = iTeacherDao.getTeacherByName(oldName);
        Subject subject = iSubjectDao.getSubjectByName(subjectName);

        Validator.checkNullObject(Teacher.class.getSimpleName(), teacher);
        Validator.checkNullObject(Subject.class.getSimpleName(), subject);

        if (!oldName.equals(newName)) {
            Validator.checkAlreadyExist(iTeacherDao.getTeacherByName(newName));
        }

        teacher.setName(newName);

        if (experience != teacher.getExperience()) {
            teacher.setExperience(experience);
        }

        if (subject.equals(teacher.getSubject())) {
            teacher.setSubject(subject);
        }

        return iTeacherDao.updateTeacher(teacher);
    }

    @Override
    public boolean setSubject(String teacherName, String subjectName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException {

        Validator.teacherName(teacherName);
        Validator.subjectName(subjectName);

        Teacher teacher = iTeacherDao.getTeacherByName(teacherName);
        Subject subject = iSubjectDao.getSubjectByName(subjectName);

        Validator.checkNullObject(Teacher.class.getSimpleName(), teacher);
        Validator.checkNullObject(Subject.class.getSimpleName(), subject);

        teacher.setSubject(subject);

        return iTeacherDao.updateTeacher(teacher);
    }

    @Override
    public Teacher getTeacherByName(String name) throws EntityNotFoundException {
        return getSingleResult(NAME, name);
    }

    @Override
    public List<Teacher> getAllTeachers(int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(ALL, null, from, quantity);
    }

    @Override
    public List<Teacher> getTeacherByNameLike(String name, int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(NAME, "%" + name + "%", from, quantity);
    }

    @Override
    public List<Teacher> getAllTeachersBySubject(String subjectName, int from, int quantity) throws EntityNotFoundException {
        Subject subject = iSubjectDao.getSubjectByName(subjectName);

        Validator.checkNullObject(Subject.class.getSimpleName(), subject);

        return getAllTeachers(SUBJECT, subject, from, quantity);
    }

    @Override
    public List<Teacher> getMaxExperiencedTeachers(int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(MAX, null, from, quantity);
    }

    @Override
    public List<Teacher> getMinExperiencedTeachers(int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(MIN, null, from, quantity);
    }

    @Override
    public List<Teacher> getTeachersMoreThanExperience(int experience, int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(EXPERIENCE, experience, from, quantity);
    }

    /**
     * @param operation(String) - ALL - for getAllTeachers() method
     *                          - SUBJECT - for getTeachersBySubject() method
     *                          - EXPERIENCE - for getTeacherByExperience() method
     *                          - MAX - for getMaxExperiencedTeachers() method
     *                          - MIN - for getMinExperiencedTeachers() method
     *                          - NAME - for getTeacherByNameLike() method
     * @param param(Object)     - subject_id(Long) - subject id, for SUBJECT operation
     *                          - experience(Integer) - teacher experience, for EXPERIENCE operation
     *                          - name(String) - teacher name, for NAME operation
     * @param from(int)         the starting row of entries returning
     * @param quantity(int)     the total number of entries returning
     * @return List of teachers
     * @throws EntityNotFoundException
     */
    private List<Teacher> getAllTeachers(String operation, Object param, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        List<Teacher> teacherList = new ArrayList<>();

        switch (operation) {
            case ALL:
                teacherList = iTeacherDao.getAllTeachers(from, quantity);
                break;
            case SUBJECT:
                teacherList = iTeacherDao.getTeachersBySubject((Subject) param, from, quantity);
                break;
            case EXPERIENCE:
                teacherList = iTeacherDao.getTeacherByExperience((Integer) param, from, quantity);
                break;
            case MAX:
                teacherList = iTeacherDao.getMaxExperiencedTeachers(from, quantity);
                break;
            case MIN:
                teacherList = iTeacherDao.getMinExperiencedTeachers(from, quantity);
                break;
            case NAME:
                teacherList = iTeacherDao.getTeacherByNameLike(String.valueOf(param), from, quantity);
                break;
        }

        Validator.checkListIsEmpty(Teacher.class.getSimpleName(), teacherList);

        return teacherList;
    }

    /**
     * @param operation(String) - ALL - for getTeacher() method
     *                          - NAME - for getTeacherByName() method
     * @param param(Object)     - name(String) - teacher name, for ALL and NAME operations
     *                          - experience(Integer) - teacher experience, for ALL operation
     *                          - subject_id(Long) - subject id, for ALL operation
     * @return Teacher entity
     * @throws EntityNotFoundException
     */
    private Teacher getSingleResult(String operation, Object... param) throws EntityNotFoundException {
        Teacher teacher = null;

        switch (operation) {
            case ALL:
                teacher = iTeacherDao.getTeacher(new Teacher(String.valueOf(param[0]), (Integer) param[1]));
                break;
            case NAME:
                teacher = iTeacherDao.getTeacherByName(String.valueOf(param[0]));
                break;
        }

        Validator.checkNullObject(Teacher.class.getSimpleName(), teacher);

        return teacher;
    }
}
