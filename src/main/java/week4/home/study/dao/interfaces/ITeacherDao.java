package week4.home.study.dao.interfaces;

import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;

import java.util.List;

public interface ITeacherDao {
    boolean addTeacher(Teacher teacher) throws ComingNullObjectException, EntityAlreadyExistException;

    boolean removeTeacher(long id);

    boolean updateTeacher(Teacher teacher) throws ComingNullObjectException, EntityAlreadyExistException;

    Teacher getTeacher(Teacher teacher) throws EntityNotFoundException;

    Teacher getTeacherByName(String name) throws EntityNotFoundException;

    Teacher getTeacherById(long id);

    List<Teacher> getTeachersBySubject(Subject subject, int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getAllTeachers(int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getTeacherByExperience(int years, int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getMinExperiencedTeachers(int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getMaxExperiencedTeachers(int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getTeacherByNameLike(String name, int from, int quantity) throws EntityNotFoundException;
}
