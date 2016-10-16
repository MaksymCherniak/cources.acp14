package week4.home.study.dao.interfaces;

import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

public interface ISubjectDao {
    boolean addSubject(Subject subject) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException;

    boolean removeSubject(long id) throws EntityNotFoundException;

    boolean updateSubject(Subject subject) throws ComingNullObjectException, OperationFailedException;

    Subject getSubject(Subject subject);

    Subject getSubjectById(long id);

    List<Subject> getAllSubjects(int from, int quantity);
}
