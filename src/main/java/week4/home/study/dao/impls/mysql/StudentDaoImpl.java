package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.dao.repositories.StudentRepository;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class StudentDaoImpl implements IStudentDao {
    private static Logger log = Logger.getLogger(StudentDaoImpl.class.getName());
    @Autowired
    private StudentRepository studentRepository;

    public StudentDaoImpl() {
    }

    @Override
    public boolean addStudent(Student student) throws ComingNullObjectException, EntityAlreadyExistException {

        if (student == null) {
            throw new ComingNullObjectException(Student.class.getName(), OPERATION_ADD);
        }

        if (studentRepository.getStudent(student.getName(), student.getGroups().getId()) != null) {
            throw new EntityAlreadyExistException(student);
        }

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
    public boolean updateStudent(Student student) throws ComingNullObjectException, EntityAlreadyExistException {

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

    @Override
    public Student getStudentById(long id) {
        return studentRepository.getOne(id);
    }

    @Override
    public Student getStudentByName(String name) throws EntityNotFoundException {
        return getSingleResult(NAME, name);
    }

    @Override
    public Student getStudent(Student student) throws EntityNotFoundException {
        return getSingleResult(GROUP, student.getName(), student.getGroups().getId());
    }

    @Override
    public List<Student> getAllStudents(int from, int quantity) throws EntityNotFoundException {
        return getAllStudentsBy(ALL, null, from, quantity);
    }

    @Override
    public List<Student> getStudentsByGroup(Groups groups, int from, int quantity) throws EntityNotFoundException {
        return getAllStudentsBy(GROUP, groups.getId(), from, quantity);
    }

    @Override
    public List<Student> getStudentsByNameLike(String name, int from, int quantity) throws EntityNotFoundException {
        return getAllStudentsBy(NAME, name, from, quantity);
    }

    /**
     * @param operation(String) - ALL - for getAllStudents() method
     *                          - NAME - for getStudentsByNameLike() method
     *                          - GROUP - for getStudentsByGroup() method
     * @param param(Object)     - name(String) - student name, for NAME operation
     *                          - group_id(Long) - group id, fro GROUP operation
     * @param from(int)         the starting row of entries returning
     * @param quantity(int)     the total number of entries returning
     * @return List of students
     * @throws EntityNotFoundException
     */
    private List<Student> getAllStudentsBy(String operation, Object param, int from, int quantity) throws EntityNotFoundException {
        List<Student> studentList = new ArrayList<>();
        switch (operation) {
            case ALL:
                studentList = studentRepository.findAll(new PageRequest(from, quantity)).getContent();
                break;
            case NAME:
                studentList = studentRepository.getStudentsByNameLike((String) param, new PageRequest(from, quantity));
                break;
            case GROUP:
                studentList = studentRepository.getStudentsByGroup((Long) param, new PageRequest(from, quantity));
                break;
        }

        if (studentList.size() != 0) {
            return studentList;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        throw new EntityNotFoundException(Student.class.getName());
    }

    /**
     * @param operation(String) - NAME - for getStudentByName() method
     *                          - GROUP - for getStudent() method
     * @param param(Object)     - name(String) - student name, for NAME and GROUP operations
     *                          - group_id(Long) - group id, for GROUP operation
     * @return Student entity
     * @throws EntityNotFoundException
     */
    private Student getSingleResult(String operation, Object... param) throws EntityNotFoundException {
        Student student = null;
        switch (operation) {
            case NAME:
                student = studentRepository.getStudentByName((String) param[0]);
                break;
            case GROUP:
                student = studentRepository.getStudent((String) param[0], (Long) param[1]);
                break;
        }

        if (student != null) {
            return student;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        throw new EntityNotFoundException(Student.class.getName());
    }
}
