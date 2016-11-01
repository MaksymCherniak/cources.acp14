package week4.home.study.service.interfaces;

import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;

import java.util.List;

public interface ISubjectService {
    boolean addSubject(String name, String description) throws EntityAlreadyExistException, ComingNullObjectException;

    boolean removeSubject(String name) throws EntityNotFoundException;

    boolean updateSubject(String oldName, String newName, String newDescription) throws EntityNotFoundException, EntityAlreadyExistException, ComingNullObjectException;

    Subject getSubjectByName(String name) throws EntityNotFoundException;

    Subject getSubjectByTeacher(String teacherName) throws EntityNotFoundException;

    List<Subject> getAllSubjects(int from, int quantity) throws EntityNotFoundException;

    List<Subject> getAllSubjectsByGroupName(String groupName, int from, int quantity) throws EntityNotFoundException;

    List<Subject> getAllSubjectsByNameLike(String name, int from, int quantity) throws EntityNotFoundException;
}
