package week4.home.study.dao.interfaces;

import week4.home.study.entity.Subject;

import java.util.List;

public interface ISubjectDao {
    boolean addSubject(Subject subject);

    boolean removeSubject(long id);

    boolean updateSubject(Subject subject);

    Subject getSubject(Subject subject);

    Subject getSubjectByName(String name);

    Subject getSubjectById(long id);

    List<Subject> getAllSubjects(int from, int quantity);

    List<Subject> getAllSubjectsByNameLike(String name, int from, int quantity);
}
