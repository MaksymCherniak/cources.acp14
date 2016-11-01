package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.dao.repositories.TeacherRepository;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class TeacherDaoImpl implements ITeacherDao {
    private static Logger log = Logger.getLogger(TeacherDaoImpl.class.getName());
    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherDaoImpl() {
    }

    @Override
    public boolean addTeacher(Teacher teacher) throws ComingNullObjectException, EntityAlreadyExistException {

        if (teacher == null || teacher.getName() == null) {
            throw new ComingNullObjectException(Teacher.class.getName(), OPERATION_ADD);
        }

        if (teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId()) != null) {
            throw new EntityAlreadyExistException(teacher);
        }

        teacherRepository.save(teacher);

        log.info(teacher.toString() + LOG_OPERATION_ADD);
        return true;
    }

    @Override
    public boolean removeTeacher(long id) {
        Teacher teacher = getTeacherById(id);

        teacherRepository.delete(teacher);

        log.info(teacher.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) throws ComingNullObjectException, EntityAlreadyExistException {

        if (teacher == null || teacher.getName() == null) {
            throw new ComingNullObjectException(Teacher.class.getName(), OPERATION_UPDATE);
        }

        if (teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId()) != null) {
            throw new EntityAlreadyExistException(teacher);
        }

        teacherRepository.updateTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId(), teacher.getId());

        log.info(teacher.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    @Override
    public Teacher getTeacher(Teacher teacher) throws EntityNotFoundException {
        return getSingleResult(ALL, teacher.getName(), teacher.getExperience(), teacher.getSubject().getId());
    }

    @Override
    public Teacher getTeacherByName(String name) throws EntityNotFoundException {
        return getSingleResult(NAME, name);
    }

    @Override
    public Teacher getTeacherById(long id) {
        return teacherRepository.findOne(id);
    }

    @Override
    public List<Teacher> getTeachersBySubject(Subject subject, int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(SUBJECT, subject.getId(), from, quantity);
    }

    @Override
    public List<Teacher> getAllTeachers(int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(ALL, null, from, quantity);
    }

    @Override
    public List<Teacher> getTeacherByExperience(int year, int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(EXPERIENCE, year, from, quantity);
    }

    @Override
    public List<Teacher> getMinExperiencedTeachers(int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(MIN, null, from, quantity);
    }

    @Override
    public List<Teacher> getMaxExperiencedTeachers(int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(MAX, null, from, quantity);
    }

    @Override
    public List<Teacher> getTeacherByNameLike(String name, int from, int quantity) throws EntityNotFoundException {
        return getAllTeachers(NAME, name, from, quantity);
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
        List<Teacher> teacherList = new ArrayList<>();
        switch (operation) {
            case ALL:
                teacherList = teacherRepository.findAll(new PageRequest(from, quantity)).getContent();
                break;
            case SUBJECT:
                teacherList = teacherRepository.getTeachersBySubject((Long) param, new PageRequest(from, quantity));
                break;
            case EXPERIENCE:
                teacherList = teacherRepository.getTeachersMoreThanExperience((Integer) param, new PageRequest(from, quantity));
                break;
            case MAX:
                teacherList = teacherRepository.getMaxExperiencedTeachers(new PageRequest(from, quantity));
                break;
            case MIN:
                teacherList = teacherRepository.getMinExperiencedTeachers(new PageRequest(from, quantity));
                break;
            case NAME:
                teacherList = teacherRepository.getTeacherByNameLike((String) param, new PageRequest(from, quantity));
                break;
        }

        if (teacherList.size() != 0) {
            return teacherList;
        }

        log.info(ERROR_TEACHER_NOT_FOUND);
        throw new EntityNotFoundException(Teacher.class.getName());
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
                teacher = teacherRepository.getTeacher((String) param[0], (Integer) param[1], (Long) param[2]);
                break;
            case NAME:
                teacher = teacherRepository.getTeacherByName((String) param[0]);
                break;
        }

        if (teacher != null) {
            return teacher;
        }

        log.info(ERROR_TEACHER_NOT_FOUND);
        throw new EntityNotFoundException(Teacher.class.getName());
    }
}
