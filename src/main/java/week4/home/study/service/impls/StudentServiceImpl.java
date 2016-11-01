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
import week4.home.study.service.interfaces.IStudentService;

import java.util.List;

import static week4.home.study.start.AppStaticValues.DEFAULT_QUANTITY_VALUE;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private IStudentDao iStudentDao;
    @Autowired
    private IGroupDao iGroupDao;

    @Override
    public boolean addStudent(String name, String group) throws EntityAlreadyExistException, ComingNullObjectException
            , EntityNotFoundException {

        Student student = new Student(name);
        student.setGroups(iGroupDao.getGroup(new Groups(group)));

        return iStudentDao.addStudent(student);
    }

    @Override
    public boolean removeStudent(String name) throws EntityNotFoundException {
        return iStudentDao.removeStudent(iStudentDao.getStudentByName(name).getId());
    }

    @Override
    public boolean updateStudent(String oldName, String newName, String groupName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException {

        Student student = iStudentDao.getStudentByName(oldName);

        if (newName != null && !newName.equals("")) {
            student.setName(newName);
        }

        if (groupName != null && !groupName.equals("")) {
            student.setGroups(iGroupDao.getGroup(new Groups(groupName)));
        }

        return iStudentDao.updateStudent(student);
    }

    @Override
    public boolean setGroup(String studentName, String groupName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException {

        Groups group = iGroupDao.getGroup(new Groups(groupName));
        Student student = iStudentDao.getStudentByName(studentName);

        student.setGroups(group);
        return iStudentDao.updateStudent(student);
    }

    @Override
    public Student getStudent(String name, String group) throws EntityNotFoundException {

        Student student = iStudentDao.getStudentByName(name);
        student.setGroups(iGroupDao.getGroup(new Groups(group)));

        return iStudentDao.getStudent(student);
    }

    @Override
    public Student getStudentByName(String name) throws EntityNotFoundException {
        return iStudentDao.getStudentByName(name);
    }

    @Override
    public List<Student> getAllStudents(int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iStudentDao.getAllStudents(from, quantity);
    }

    @Override
    public List<Student> getAllStudentsByGroupName(String groupName, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iStudentDao.getStudentsByGroup(iGroupDao.getGroup(new Groups(groupName)), from, quantity);
    }

    @Override
    public List<Student> getAllStudentsLike(String name, int from, int quantity) throws EntityNotFoundException {
        if (quantity == 0) {
            quantity = DEFAULT_QUANTITY_VALUE;
        }

        return iStudentDao.getStudentsByNameLike("%" + name + "%", from, quantity);
    }
}
