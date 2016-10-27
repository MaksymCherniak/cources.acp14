package week4.home.study.dao.interfaces;

import week4.home.study.entity.Groups;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

public interface IGroupDao {
    boolean addGroup(Groups groups) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException;

    boolean removeGroup(long id);

    boolean updateGroup(Groups groups) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException;

    Groups getGroup(Groups groups) throws EntityNotFoundException;

    Groups getGroupById(long id);

    List<Groups> getAllGroups(int from, int quantity) throws EntityNotFoundException;
}
