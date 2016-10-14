package week4.home.jdbc.dao.services;

import week4.home.jdbc.entity.Teacher;

import java.util.List;

public interface ITeacherService {
    void addTeacher(Teacher teacher);

    void removeTeacher(long id);

    void updateTeacher(Teacher teacher);

    Teacher getTeacherById(long id);

    List<Teacher> getAllTeachers();

    List<Teacher> getTeacherByExperience();

    List<Teacher> getMinExperienced();

    List<Teacher> getMaxExperienced();
}
