package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.dao.repositories.StudentRepository;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class StudentDaoImpl implements IStudentDao {
    private static Logger log = Logger.getLogger(StudentDaoImpl.class.getName());
    @Autowired
    private StudentRepository studentRepository;

    public StudentDaoImpl() {
    }

    public boolean addStudent(Student student) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        if (student == null) {
            throw new ComingNullObjectException(Student.class.getName(), OPERATION_ADD);
        }

        if (studentRepository.getStudent(student.getName(), student.getGroups().getId()) != null) {
            throw new EntityAlreadyExistException(student);
        }

        if (studentRepository.save(student) != null) {
            log.info(student.toString() + LOG_OPERATION_ADD);
            return true;
        }

        throw new OperationFailedException(Student.class.getName(), OPERATION_ADD);
    }

    public boolean removeStudent(long id) {
        Student student = getStudentById(id);

        studentRepository.delete(student);

        log.info(student.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    public boolean updateStudent(Student student) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        if (student == null || student.getName() == null || student.getGroups() == null) {
            throw new ComingNullObjectException(Student.class.getName(), OPERATION_UPDATE);
        }

        if (studentRepository.getStudent(student.getName(), student.getGroups().getId()) != null) {
            throw new EntityAlreadyExistException(student);
        }

        studentRepository.updateStudent(student.getName(), student.getGroups().getId(), student.getId());
        log.info(student.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    public Student getStudentById(long id) {
        return studentRepository.getOne(id);
    }

    public Student getStudent(Student student) throws EntityNotFoundException {
        Student result = studentRepository.getStudent(student.getName(), student.getGroups().getId());
        if (result != null) {
            return result;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        throw new EntityNotFoundException(Student.class.getName());
    }


    public List<Student> getAllStudents(int from, int quantity) throws EntityNotFoundException {
        List<Student> students = studentRepository.findAll();
        if (students != null) {
            return students;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        throw new EntityNotFoundException(Student.class.getName());
    }

    public List<Student> getStudentsByGroup(Groups groups, int quantity) throws EntityNotFoundException {
        List<Student> students = studentRepository.getStudentsByGroup(groups.getId());
        if (students != null) {
            return students;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        throw new EntityNotFoundException(Student.class.getName());
    }
}
