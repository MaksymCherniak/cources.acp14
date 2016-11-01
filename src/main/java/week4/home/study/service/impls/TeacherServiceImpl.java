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
import week4.home.study.service.interfaces.ITeacherService;

import java.util.List;

import static week4.home.study.start.AppStaticValues.DEFAULT_QUANTITY_VALUE;

@Service
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    private ITeacherDao iTeacherDao;
    @Autowired
    private ISubjectDao iSubjectDao;

    @Override
    public boolean addTeacher(String name, int experience) throws EntityAlreadyExistException
            , ComingNullObjectException {

        return iTeacherDao.addTeacher(new Teacher(name, experience));
    }

    @Override
    public boolean removeTeacher(String name) throws EntityNotFoundException {
        return iTeacherDao.removeTeacher(iTeacherDao.getTeacherByName(name).getId());
    }

    @Override
    public boolean updateTeacher(String oldName, String newName, int experience, String subjectName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException {

        Teacher teacher = iTeacherDao.getTeacherByName(oldName);

        if (newName != null && !newName.equals("")) {
            teacher.setName(newName);
        }

        if (experience != teacher.getExperience()) {
            teacher.setExperience(experience);
        }

        if (subjectName != null && !iSubjectDao.getSubjectByName(subjectName).equals(teacher.getSubject())) {
            teacher.setSubject(iSubjectDao.getSubjectByName(subjectName));
        }

        return iTeacherDao.updateTeacher(teacher);
    }

    @Override
    public boolean setSubject(String teacherName, String subjectName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException {

        Subject subject = iSubjectDao.getSubjectByName(subjectName);
        Teacher teacher = iTeacherDao.getTeacherByName(teacherName);

        teacher.setSubject(subject);

        return iTeacherDao.updateTeacher(teacher);
    }

    @Override
    public Teacher getTeacherByName(String name) throws EntityNotFoundException {
        return iTeacherDao.getTeacherByName(name);
    }

    @Override
    public List<Teacher> getAllTeachers(int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iTeacherDao.getAllTeachers(from, quantity);
    }

    @Override
    public List<Teacher> getTeacherByNameLike(String name, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iTeacherDao.getTeacherByNameLike("%" + name + "%", from, quantity);
    }

    @Override
    public List<Teacher> getAllTeachersBySubject(String subjectName, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        Subject subject = iSubjectDao.getSubjectByName(subjectName);

        return iTeacherDao.getTeachersBySubject(subject, from, quantity);
    }

    @Override
    public List<Teacher> getMaxExperiencedTeachers(int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iTeacherDao.getMaxExperiencedTeachers(from, quantity);
    }

    @Override
    public List<Teacher> getMinExperiencedTeachers(int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iTeacherDao.getMinExperiencedTeachers(from, quantity);
    }

    @Override
    public List<Teacher> getTeachersMoreThanExperience(int experience, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iTeacherDao.getTeacherByExperience(experience, from, quantity);
    }
}
