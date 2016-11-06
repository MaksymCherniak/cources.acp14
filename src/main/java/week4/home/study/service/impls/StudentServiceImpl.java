package week4.home.study.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.InvalidNameFormatException;
import week4.home.study.main.Validator;
import week4.home.study.service.interfaces.IStudentService;

import java.util.ArrayList;
import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private IStudentDao iStudentDao;
    @Autowired
    private IGroupDao iGroupDao;

    @Override
    public boolean addStudent(String name, String group) throws EntityAlreadyExistException, ComingNullObjectException
            , EntityNotFoundException, InvalidNameFormatException {

        Validator.studentName(name);
        Validator.groupName(group);

        Student student = new Student(name);
        Groups groups = iGroupDao.getGroup(new Groups(group));

        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        student.setGroups(groups);

        Validator.checkAlreadyExist(iStudentDao.getStudent(student));

        return iStudentDao.addStudent(student);
    }

    @Override
    public boolean removeStudent(String name) throws EntityNotFoundException {
        Student student = iStudentDao.getStudentByName(name);

        Validator.checkNullObject(Student.class.getSimpleName(), student);

        return iStudentDao.removeStudent(student.getId());
    }

    @Override
    public boolean updateStudent(String oldName, String newName, String groupName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException {

        Validator.studentName(oldName);
        Validator.studentName(newName);
        Validator.groupName(groupName);

        Student student = iStudentDao.getStudentByName(oldName);
        Groups groups = iGroupDao.getGroup(new Groups(groupName));

        Validator.checkNullObject(Student.class.getSimpleName(), student);
        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        if (!oldName.equals(newName)) {
            Validator.checkAlreadyExist(iStudentDao.getStudent(new Student(newName, groups)));
        }

        student.setName(newName);
        student.setGroups(groups);

        return iStudentDao.updateStudent(student);
    }

    @Override
    public boolean setGroup(String studentName, String groupName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException {

        Validator.studentName(studentName);
        Validator.groupName(groupName);

        Groups groups = iGroupDao.getGroup(new Groups(groupName));
        Student student = iStudentDao.getStudentByName(studentName);

        Validator.checkNullObject(Student.class.getSimpleName(), student);
        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        student.setGroups(groups);
        return iStudentDao.updateStudent(student);
    }

    @Override
    public Student getStudent(String name, String group) throws EntityNotFoundException, ComingNullObjectException
            , InvalidNameFormatException {

        Validator.studentName(name);
        Validator.groupName(group);

        Groups groups = iGroupDao.getGroup(new Groups(group));

        Validator.checkNullObject(Groups.class.getSimpleName(), groups);

        return getSingleResult(GROUP, name, groups);
    }

    @Override
    public Student getStudentByName(String name) throws EntityNotFoundException, InvalidNameFormatException, ComingNullObjectException {

        Validator.studentName(name);

        return getSingleResult(NAME, name);
    }

    @Override
    public List<Student> getAllStudents(int from, int quantity) throws EntityNotFoundException {
        return getAllStudentsBy(ALL, null, from, quantity);
    }

    @Override
    public List<Student> getAllStudentsByGroupName(String groupName, int from, int quantity) throws EntityNotFoundException {
        return getAllStudentsBy(GROUP, groupName, from, quantity);
    }

    @Override
    public List<Student> getAllStudentsLike(String name, int from, int quantity) throws EntityNotFoundException {
        return getAllStudentsBy(NAME, "%" + name + "%", from, quantity);
    }

    /**
     * @param operation(String) - NAME - for getStudentByName() method
     *                          - GROUP - for getStudent() method
     * @param param(Object)     - name(String) - student name, for NAME and GROUP operations
     *                          - group_id(Long) - groups id, for GROUP operation
     * @return Student entity
     * @throws EntityNotFoundException
     */
    private Student getSingleResult(String operation, Object... param) throws EntityNotFoundException {
        Student student = null;

        switch (operation) {
            case NAME:
                student = iStudentDao.getStudentByName(String.valueOf(param[0]));
                break;
            case GROUP:
                student = iStudentDao.getStudent(new Student(String.valueOf(param[0]), (Groups) param[1]));
                break;
        }

        Validator.checkNullObject(Student.class.getSimpleName(), student);

        return student;
    }

    /**
     * @param operation(String) - ALL - for getAllStudents() method
     *                          - NAME - for getStudentsByNameLike() method
     *                          - GROUP - for getStudentsByGroup() method
     * @param param(Object)     - name(String) - student name, for NAME operation
     *                          - group_id(Long) - groups id, fro GROUP operation
     * @param from(int)         the starting row of entries returning
     * @param quantity(int)     the total number of entries returning
     * @return List of students
     * @throws EntityNotFoundException
     */
    private List<Student> getAllStudentsBy(String operation, Object param, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        List<Student> studentList = new ArrayList<>();

        switch (operation) {
            case ALL:
                studentList = iStudentDao.getAllStudents(from, quantity);
                break;
            case NAME:
                studentList = iStudentDao.getStudentsByNameLike(String.valueOf(param), from, quantity);
                break;
            case GROUP:
                studentList = iStudentDao.getStudentsByGroup((Groups) param, from, quantity);
                break;
        }

        Validator.checkListIsEmpty(Student.class.getSimpleName(), studentList);

        return studentList;
    }
}
