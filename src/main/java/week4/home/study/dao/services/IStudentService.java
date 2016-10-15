package week4.home.study.dao.services;

import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;

import java.util.List;

public interface IStudentService {
    boolean addStudent(Student student);

    boolean removeStudent(long id);

    boolean updateStudent(Student student);

    Student getStudentById(long id);

    Student getStudent(Student student);

    List<Student> getAllStudents();

    List<Student> getStudentsByGroup(Groups groups);


}
