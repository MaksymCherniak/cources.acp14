package week4.home.study.service.interfaces;

import week4.home.study.entity.Groups;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.InvalidNameFormatException;

import java.util.List;

public interface IGroupService {
    boolean addGroup(String name) throws EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException, EntityNotFoundException;

    boolean removeGroup(String name) throws EntityNotFoundException;

    boolean updateGroup(String oldName, String newName) throws EntityNotFoundException, EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException;

    Groups getGroupByName(String name) throws EntityNotFoundException, ComingNullObjectException, InvalidNameFormatException;

    Groups getGroupByStudentName(String student) throws EntityNotFoundException;

    List<Groups> getAllGroups(int from, int quantity) throws EntityNotFoundException;

    List<Groups> getGroupsLike(String name, int from, int quantity) throws EntityNotFoundException;

    List<Groups> getGroupsBySubject(String subject, int from, int quantity) throws EntityNotFoundException;

    boolean setSubject(String name, String subject) throws EntityNotFoundException, EntityAlreadyExistException, ComingNullObjectException, InvalidNameFormatException;
}
