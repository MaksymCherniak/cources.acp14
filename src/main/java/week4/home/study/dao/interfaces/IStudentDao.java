package week4.home.study.dao.interfaces;

import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;

import java.util.List;

public interface IStudentDao {
    boolean addStudent(Student student) throws ComingNullObjectException, EntityAlreadyExistException;

    boolean removeStudent(long id);

    boolean updateStudent(Student student) throws ComingNullObjectException, EntityAlreadyExistException;

    Student getStudentById(long id);

    Student getStudentByName(String name) throws EntityNotFoundException;

    Student getStudent(Student student) throws EntityNotFoundException;

    List<Student> getAllStudents(int from, int quantity) throws EntityNotFoundException;

    List<Student> getStudentsByGroup(Groups groups, int from, int quantity) throws EntityNotFoundException;

    List<Student> getStudentsByNameLike(String name, int from, int quantity) throws EntityNotFoundException;
}
