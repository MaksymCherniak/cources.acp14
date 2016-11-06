package week4.home.study.dao.interfaces;

import week4.home.study.entity.Groups;

import java.util.List;

public interface IGroupDao {
    boolean addGroup(Groups groups);

    boolean removeGroup(long id);

    boolean updateGroup(Groups groups);

    boolean updateAll(Groups groups);

    Groups getGroup(Groups groups);

    Groups getGroupById(long id);

    List<Groups> getAllGroups(int from, int quantity);

    List<Groups> getGroupsLike(String name, int from, int quantity);

    List<Groups> getGroupsBySubject(String subject, int from, int quantity);
}
