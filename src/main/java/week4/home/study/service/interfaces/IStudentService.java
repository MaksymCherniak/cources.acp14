package week4.home.study.service.interfaces;

import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;

import java.util.List;

public interface IStudentService {
    boolean addStudent(String name, String group) throws EntityAlreadyExistException, ComingNullObjectException, EntityNotFoundException;

    boolean removeStudent(String name) throws EntityNotFoundException;

    boolean updateStudent(String oldName, String newName, String groupName) throws EntityNotFoundException, EntityAlreadyExistException, ComingNullObjectException;

    boolean setGroup(String studentName, String groupName) throws EntityNotFoundException, EntityAlreadyExistException, ComingNullObjectException;

    Student getStudentByName(String name) throws EntityNotFoundException;

    Student getStudent(String name, String group) throws EntityNotFoundException;

    List<Student> getAllStudents(int from, int quantity) throws EntityNotFoundException;

    List<Student> getAllStudentsByGroupName(String groupName, int from, int quantity) throws EntityNotFoundException;

    List<Student> getAllStudentsLike(String name, int from, int quantity) throws EntityNotFoundException;
}
