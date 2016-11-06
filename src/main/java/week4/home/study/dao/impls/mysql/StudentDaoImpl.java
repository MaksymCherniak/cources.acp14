package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.dao.repositories.StudentRepository;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;

import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

public class StudentDaoImpl implements IStudentDao {
    private static Logger log = Logger.getLogger(StudentDaoImpl.class.getName());
    @Autowired
    private StudentRepository studentRepository;

    public StudentDaoImpl() {
    }

    @Override
    public boolean addStudent(Student student) {

        studentRepository.save(student);

        log.info(student.toString() + LOG_OPERATION_ADD);
        return true;
    }

    @Override
    public boolean removeStudent(long id) {
        Student student = getStudentById(id);

        studentRepository.delete(student);

        log.info(student.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    @Override
    public boolean updateStudent(Student student) {

        studentRepository.updateStudent(student.getName(), student.getGroups().getId(), student.getId());

        log.info(student.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    @Override
    public Student getStudentById(long id) {
        return studentRepository.getOne(id);
    }

    @Override
    public Student getStudentByName(String name) {
        return studentRepository.getStudentByName(name);
    }

    @Override
    public Student getStudent(Student student) {
        return studentRepository.getStudent(student.getName(), student.getGroups().getId());
    }

    @Override
    public List<Student> getAllStudents(int from, int quantity) {
        return studentRepository.findAll(new PageRequest(from, quantity)).getContent();
    }

    @Override
    public List<Student> getStudentsByGroup(Groups groups, int from, int quantity) {
        return studentRepository.getStudentsByGroup(groups.getId(), new PageRequest(from, quantity));
    }

    @Override
    public List<Student> getStudentsByNameLike(String name, int from, int quantity) {
        return studentRepository.getStudentsByNameLike(name, new PageRequest(from, quantity));
    }
}
