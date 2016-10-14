package week4.home.jdbc.dao.services;

import week4.home.jdbc.entity.Group;
import week4.home.jdbc.entity.Student;

import java.util.List;

public interface IStudentService {
    boolean addStudent(Student student);

    boolean removeStudent(long id);

    boolean updateStudent(Student student);

    Student getStudentById(long id);

    Student findStudent(Student student);

    List<Student> getAllStudents();

    List<Student> getStudentsByGroup(Group group);
}
