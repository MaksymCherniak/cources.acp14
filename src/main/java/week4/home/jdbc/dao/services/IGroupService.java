package week4.home.jdbc.dao.services;

import week4.home.jdbc.entity.Group;

import java.util.List;

public interface IGroupService {
    void addGroup(Group group);

    void removeGroup(long id);

    void updateGroup(Group group);

    Group getGroupById(long id);

    List<Group> getAllGroups();
}
