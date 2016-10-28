package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.dao.repositories.SubjectRepository;
import week4.home.study.dao.repositories.TeacherRepository;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class TeacherDaoImpl implements ITeacherDao {
    private static Logger log = Logger.getLogger(TeacherDaoImpl.class.getName());
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public TeacherDaoImpl() {
    }

    public boolean addTeacher(Teacher teacher) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        if (teacher == null || teacher.getName() == null) {
            throw new ComingNullObjectException(Teacher.class.getName(), OPERATION_ADD);
        }

        if (teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId()) != null) {
            throw new EntityAlreadyExistException(teacher);
        }

        if (teacherRepository.save(teacher) != null) {
            log.info(teacher.toString() + LOG_OPERATION_ADD);
            return true;
        }

        throw new OperationFailedException(Teacher.class.getName(), OPERATION_ADD);
    }

    public boolean removeTeacher(long id) {
        Teacher teacher = getTeacherById(id);

        teacherRepository.delete(teacher);

        log.info(teacher.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    public boolean updateTeacher(Teacher teacher) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
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

    public Teacher getTeacher(Teacher teacher) throws EntityNotFoundException {
        Teacher result = teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId());
        if (result != null) {
            return result;
        }

        log.info(ERROR_TEACHER_NOT_FOUND);
        throw new EntityNotFoundException(Teacher.class.getName());
    }

    public Teacher getTeacherByName(String name) throws EntityNotFoundException {
        Teacher result = teacherRepository.getTeacherByName(name);
        if (result != null) {
            return result;
        }

        log.info(ERROR_TEACHER_NOT_FOUND);
        throw new EntityNotFoundException(Teacher.class.getName());
    }

    public Teacher getTeacherById(long id) {
        return teacherRepository.findOne(id);
    }

    public List<Teacher> getTeachersBySubject(String subjectName) {
        return teacherRepository.getTeachersBySubject(subjectRepository.getSubject(subjectName).getId());
    }

    public List<Teacher> getAllTeachers(int from, int quantity) {
        return teacherRepository.findAll(new PageRequest(from, quantity)).getContent();
    }

    public List<Teacher> getTeacherByExperience(int year, int from, int quantity) {
        return teacherRepository.getTeachersMoreThanExperience(year);
    }

    public List<Teacher> getMinExperiencedTeachers(int from, int quantity) {
        return teacherRepository.getMinExpiriencedTeachers();
    }


    public List<Teacher> getMaxExperiencedTeachers(int from, int quantity) {
        return teacherRepository.getMaxExpiriencedTeachers();
    }

}
