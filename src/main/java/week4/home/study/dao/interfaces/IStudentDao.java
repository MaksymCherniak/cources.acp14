package week4.home.study.dao.interfaces;

import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;

import java.util.List;

public interface IStudentDao {
    boolean addStudent(Student student);

    boolean removeStudent(long id);

    boolean updateStudent(Student student);

    Student getStudentById(long id);

    Student getStudentByName(String name);

    Student getStudent(Student student);

    List<Student> getAllStudents(int from, int quantity);

    List<Student> getStudentsByGroup(Groups groups, int from, int quantity);

    List<Student> getStudentsByNameLike(String name, int from, int quantity);
}
