package week4.home.study.dao.services;

import week4.home.study.entity.Groups;

import java.util.List;

public interface IGroupService {
    boolean addGroup(Groups groups);

    boolean removeGroup(long id);

    boolean updateGroup(Groups groups);

    Groups getGroup(Groups groups);

    Groups getGroupById(long id);

    List<Groups> getAllGroups();
}
