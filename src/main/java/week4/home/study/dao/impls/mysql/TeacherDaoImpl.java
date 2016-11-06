package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.dao.repositories.TeacherRepository;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;

import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

public class TeacherDaoImpl implements ITeacherDao {
    private static Logger log = Logger.getLogger(TeacherDaoImpl.class.getName());
    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherDaoImpl() {
    }

    @Override
    public boolean addTeacher(Teacher teacher) {

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
    public boolean updateTeacher(Teacher teacher) {

        teacherRepository.updateTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId(), teacher.getId());

        log.info(teacher.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    @Override
    public Teacher getTeacher(Teacher teacher) {
        return teacherRepository.getTeacher(teacher.getName(), teacher.getExperience(), teacher.getSubject().getId());
    }

    @Override
    public Teacher getTeacherByName(String name) {
        return teacherRepository.getTeacherByName(name);
    }

    @Override
    public Teacher getTeacherById(long id) {
        return teacherRepository.findOne(id);
    }

    @Override
    public List<Teacher> getTeachersBySubject(Subject subject, int from, int quantity) {
        return teacherRepository.getTeachersBySubject(subject.getId(), new PageRequest(from, quantity));
    }

    @Override
    public List<Teacher> getAllTeachers(int from, int quantity) {
        return teacherRepository.findAll(new PageRequest(from, quantity)).getContent();
    }

    @Override
    public List<Teacher> getTeacherByExperience(int year, int from, int quantity) {
        return teacherRepository.getTeachersMoreThanExperience(year, new PageRequest(from, quantity));
    }

    @Override
    public List<Teacher> getMinExperiencedTeachers(int from, int quantity) {
        return teacherRepository.getMinExperiencedTeachers(new PageRequest(from, quantity));
    }

    @Override
    public List<Teacher> getMaxExperiencedTeachers(int from, int quantity) {
        return teacherRepository.getMaxExperiencedTeachers(new PageRequest(from, quantity));
    }

    @Override
    public List<Teacher> getTeacherByNameLike(String name, int from, int quantity) {
        return teacherRepository.getTeacherByNameLike(name, new PageRequest(from, quantity));
    }
}
