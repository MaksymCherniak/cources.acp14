package week4.home.study.dao.services;

import week4.home.study.entity.Teacher;

import java.util.List;

public interface ITeacherService {
    boolean addTeacher(Teacher teacher);

    boolean removeTeacher(long id);

    boolean updateTeacher(Teacher teacher);

    Teacher getTeacher(Teacher teacher);

    Teacher getTeacherById(long id);

    List<Teacher> getAllTeachers();

    List<Teacher> getTeacherByExperience();

    List<Teacher> getMinExperienced();

    List<Teacher> getMaxExperienced();
}
