package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.dao.repositories.SubjectRepository;
import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class SubjectDaoImpl implements ISubjectDao {
    private static Logger log = Logger.getLogger(SubjectDaoImpl.class.getName());
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectDaoImpl() {
    }

    @Override
    public boolean addSubject(Subject subject) throws ComingNullObjectException, EntityAlreadyExistException {

        if (subject == null || subject.getName() == null) {
            throw new ComingNullObjectException(Subject.class.getName(), OPERATION_UPDATE);
        }

        if (subjectRepository.getSubject(subject.getName(), subject.getDescription()) != null) {
            throw new EntityAlreadyExistException(subject);
        }

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
    public boolean updateSubject(Subject subject) throws ComingNullObjectException, EntityAlreadyExistException {

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

    @Override
    public Subject getSubject(Subject subject) throws EntityNotFoundException {
        return getSingleResult(ALL, subject.getName(), subject.getDescription());
    }

    @Override
    public Subject getSubjectByName(String name) throws EntityNotFoundException {
        return getSingleResult(NAME, name);
    }

    @Override
    public Subject getSubjectById(long id) {
        return subjectRepository.findOne(id);
    }

    @Override
    public List<Subject> getAllSubjects(int from, int quantity) throws EntityNotFoundException {
        return getAllSubjects(ALL, null, from, quantity);
    }

    @Override
    public List<Subject> getAllSubjectsByNameLike(String name, int from, int quantity) throws EntityNotFoundException {
        return getAllSubjects(NAME, name, from, quantity);
    }

    /**
     * @param operation(String) - ALL - for getSubject() method
     *                          - NAME - for getSubjectByName() method
     * @param param(String)     - name - subject name, for ALL and NAME operations
     *                          - description - subject description, for ALL operation
     * @return Subject entity
     * @throws EntityNotFoundException
     */
    private Subject getSingleResult(String operation, String... param) throws EntityNotFoundException {
        Subject result = null;
        switch (operation) {
            case ALL:
                result = subjectRepository.getSubject(param[0], param[1]);
                break;
            case NAME:
                result = subjectRepository.getSubjectByName(param[0]);
                break;
        }

        if (result != null) {
            return result;
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        throw new EntityNotFoundException(Subject.class.getName());
    }

    /**
     * @param operation(String) - ALL - for getAllSubjects() method
     *                          - NAME - for getAllSubjectsByNameLike() method
     * @param param(String)     - name - subject name, for NAME operation
     * @param from(int)         the starting row of entries returning
     * @param quantity(int)     the total number of entries returning
     * @return List of students
     * @throws EntityNotFoundException
     */
    private List<Subject> getAllSubjects(String operation, String param, int from, int quantity) throws EntityNotFoundException {
        List<Subject> result = new ArrayList<>();
        switch (operation) {
            case ALL:
                result = subjectRepository.findAll(new PageRequest(from, quantity)).getContent();
                break;
            case NAME:
                result = subjectRepository.getAllSubjectsByNameLike(param, new PageRequest(from, quantity));
                break;
        }

        if (result.size() != 0) {
            return result;
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        throw new EntityNotFoundException(Subject.class.getName());
    }
}
