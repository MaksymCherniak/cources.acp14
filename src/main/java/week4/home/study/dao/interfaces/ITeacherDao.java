package week4.home.study.dao.interfaces;

import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;

import java.util.List;

public interface ITeacherDao {
    boolean addTeacher(Teacher teacher);

    boolean removeTeacher(long id);

    boolean updateTeacher(Teacher teacher);

    Teacher getTeacher(Teacher teacher);

    Teacher getTeacherByName(String name);

    Teacher getTeacherById(long id);

    List<Teacher> getTeachersBySubject(Subject subject, int from, int quantity);

    List<Teacher> getAllTeachers(int from, int quantity);

    List<Teacher> getTeacherByExperience(int years, int from, int quantity);

    List<Teacher> getMinExperiencedTeachers(int from, int quantity);

    List<Teacher> getMaxExperiencedTeachers(int from, int quantity);

    List<Teacher> getTeacherByNameLike(String name, int from, int quantity);
}
