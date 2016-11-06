package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.dao.repositories.SubjectRepository;
import week4.home.study.entity.Subject;

import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

public class SubjectDaoImpl implements ISubjectDao {
    private static Logger log = Logger.getLogger(SubjectDaoImpl.class.getName());
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectDaoImpl() {
    }

    @Override
    public boolean addSubject(Subject subject) {

        subjectRepository.save(subject);

        log.info(subject.toString() + LOG_OPERATION_ADD);
        return true;
    }

    @Override
    public boolean removeSubject(long id) {
        Subject subject = getSubjectById(id);

        subjectRepository.delete(subject);

        log.info(subject.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    @Override
    public boolean updateSubject(Subject subject) {

        subjectRepository.updateSubject(subject.getName(), subject.getDescription(), subject.getId());

        log.info(subject.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    @Override
    public Subject getSubject(Subject subject) {
        return subjectRepository.getSubject(subject.getName(), subject.getDescription());
    }

    @Override
    public Subject getSubjectByName(String name) {
        return subjectRepository.getSubjectByName(name);
    }

    @Override
    public Subject getSubjectById(long id) {
        return subjectRepository.findOne(id);
    }

    @Override
    public List<Subject> getAllSubjects(int from, int quantity) {
        return subjectRepository.findAll(new PageRequest(from, quantity)).getContent();
    }

    @Override
    public List<Subject> getAllSubjectsByNameLike(String name, int from, int quantity) {
        return subjectRepository.getAllSubjectsByNameLike(name, new PageRequest(from, quantity));
    }
}
