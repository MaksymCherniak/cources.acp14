package week4.home.study.service.interfaces;

import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.InvalidNameFormatException;

import java.util.List;

public interface ITeacherService {
    boolean addTeacher(String name, int experience) throws EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException, EntityNotFoundException;

    boolean removeTeacher(String name) throws EntityNotFoundException, InvalidNameFormatException, ComingNullObjectException;

    boolean setSubject(String teacherName, String subjectName) throws EntityNotFoundException, EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException;

    boolean updateTeacher(String oldName, String newName, int experience, String subjectName) throws EntityNotFoundException, EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException;

    Teacher getTeacherByName(String name) throws EntityNotFoundException;

    List<Teacher> getAllTeachers(int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getTeacherByNameLike(String name, int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getAllTeachersBySubject(String subjectName, int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getMaxExperiencedTeachers(int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getMinExperiencedTeachers(int from, int quantity) throws EntityNotFoundException;

    List<Teacher> getTeachersMoreThanExperience(int experience, int from, int quantity) throws EntityNotFoundException;
}
