package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.dao.repositories.SubjectRepository;
import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class SubjectDaoImpl implements ISubjectDao {
    private static Logger log = Logger.getLogger(SubjectDaoImpl.class.getName());
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectDaoImpl() {
    }

    public boolean addSubject(Subject subject) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        if (subject == null || subject.getName() == null) {
            throw new ComingNullObjectException(Subject.class.getName(), OPERATION_UPDATE);
        }

        if (subjectRepository.getSubject(subject.getName(), subject.getDescription()) != null) {
            throw new EntityAlreadyExistException(subject);
        }

        if (subjectRepository.save(subject) != null) {
            log.info(subject.toString() + LOG_OPERATION_ADD);
            return true;
        }

        throw new OperationFailedException(Subject.class.getName(), OPERATION_ADD);
    }

    public boolean removeSubject(long id) {
        Subject subject = getSubjectById(id);

        subjectRepository.delete(subject);

        log.info(subject.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    public boolean updateSubject(Subject subject) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        if (subject == null || subject.getName() == null) {
            throw new ComingNullObjectException(Subject.class.getName(), OPERATION_UPDATE);
        }

        if (subjectRepository.getSubject(subject.getName(), subject.getDescription()) != null) {
            throw new EntityAlreadyExistException(subject);
        }

        subjectRepository.updateSubject(subject.getName(), subject.getDescription(), subject.getId());
        log.info(subject.toString() + LOG_OPERATION_UPDATE);
        return true;
    }

    public Subject getSubject(Subject subject) throws EntityNotFoundException {
        Subject result = subjectRepository.getSubject(subject.getName(), subject.getDescription());
        if (result != null) {
            return result;
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        throw new EntityNotFoundException(Subject.class.getName());
    }

    public Subject getSubjectByName(String name) throws EntityNotFoundException {
        Subject result = subjectRepository.getSubject(name);
        if (result != null) {
            return result;
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        throw new EntityNotFoundException(Subject.class.getName());
    }

    public Subject getSubjectById(long id) {
        return subjectRepository.findOne(id);
    }

    public List<Subject> getAllSubjects(int from, int quantity) throws EntityNotFoundException {
        List<Subject> result = subjectRepository.findAll();
        if (result.size() != 0) {
            return result;
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        throw new EntityNotFoundException(Subject.class.getName());
    }
}
