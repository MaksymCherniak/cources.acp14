package week4.home.study.dao.services;

import week4.home.study.entity.Subject;

import java.util.List;

public interface ISubjectService {
    boolean addSubject(Subject subject);

    boolean removeSubject(long id);

    boolean updateSubject(Subject subject);

    Subject getSubject(Subject subject);

    Subject getSubjectById(long id);

    List<Subject> getAllSubjects();
}
