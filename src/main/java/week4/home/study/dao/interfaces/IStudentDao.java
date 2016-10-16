package week4.home.study.dao.interfaces;

import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

public interface IStudentDao {
    boolean addStudent(Student student) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException;

    boolean removeStudent(long id) throws EntityNotFoundException;

    boolean updateStudent(Student student) throws ComingNullObjectException, OperationFailedException;

    Student getStudentById(long id);

    Student getStudent(Student student);

    List<Student> getAllStudents(int from, int quantity);

    List<Student> getStudentsByGroup(Groups groups, int quantity);


}
