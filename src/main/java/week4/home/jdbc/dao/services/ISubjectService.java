package week4.home.jdbc.dao.services;

import week4.home.jdbc.entity.Subject;

import java.util.List;

public interface ISubjectService {
    void addSubject(Subject subject);

    void removeSubject(long id);

    void updateSubject(Subject subject);

    Subject getSubjectById(long id);

    List<Subject> getAllSubjects();
}
