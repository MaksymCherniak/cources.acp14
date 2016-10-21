package week4.home.study.dao.interfaces;

import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

public interface ITeacherDao {
    boolean addTeacher(Teacher teacher) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException;

    boolean removeTeacher(long id) throws EntityNotFoundException;

    boolean updateTeacher(Teacher teacher) throws ComingNullObjectException, OperationFailedException;

    Teacher getTeacher(Teacher teacher) throws EntityNotFoundException;

    Teacher getTeacherById(long id) throws EntityNotFoundException;

    List<Teacher> getAllTeachers(int from, int quantity);

    List<Teacher> getTeacherByExperience(int years, int from, int quantity);

    List<Teacher> getMinExperiencedTeachers(int from, int quantity);

    List<Teacher> getMaxExperiencedTeachers(int from, int quantity);
}
