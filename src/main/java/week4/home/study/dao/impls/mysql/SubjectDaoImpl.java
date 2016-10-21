package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

@Service
public class SubjectDaoImpl implements ISubjectDao {
    private static final String UPDATE_SUBJECT =
            "UPDATE Subject c set c.name=:name, c.description=:description WHERE subject_id LIKE :subject_id";
    private static final String FIND_SUBJECT = "SELECT s FROM Subject s WHERE s.name LIKE :name AND s.description LIKE :description";
    private static final String GET_ALL_SUBJECTS = "SELECT g from Subject g";

    private static Logger log = Logger.getLogger(SubjectDaoImpl.class.getName());
    private EntityManager entityManager;

    public SubjectDaoImpl() {

    }

    public SubjectDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean addSubject(Subject subject) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        if (subject == null) {
            throw new ComingNullObjectException(Subject.class.getName(), OPERATION_ADD);
        }

        try {
            if (getSubject(subject) != null) {
                throw new EntityAlreadyExistException(subject);
            }
        } catch (EntityNotFoundException e) {
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(subject);
                entityManager.getTransaction().commit();
                log.info(subject.toString() + "" + LOG_OPERATION_ADD);
                return true;
            } catch (RuntimeException ex) {
                log.error(ex);
            }
        }

        throw new OperationFailedException(Subject.class.getName(), OPERATION_ADD);
    }

    public boolean removeSubject(long id) throws EntityNotFoundException {
        Subject subject = getSubjectById(id);

        entityManager.getTransaction().begin();
        entityManager.remove(subject);
        entityManager.getTransaction().commit();
        log.info("" + subject.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    public boolean updateSubject(Subject subject) throws ComingNullObjectException, OperationFailedException {
        if (subject == null) {
            throw new ComingNullObjectException(Subject.class.getName(), OPERATION_UPDATE);
        }

        try {
            if (getSubjectById(subject.getId()) != null) {
                entityManager.getTransaction().begin();
                entityManager.createQuery(UPDATE_SUBJECT)
                        .setParameter(NAME, subject.getName())
                        .setParameter(DESCRIPTION, subject.getDescription())
                        .setParameter(SUBJECT_ID, subject.getId()).executeUpdate();
                entityManager.getTransaction().commit();
                entityManager.clear();
                log.info(subject.toString() + LOG_OPERATION_UPDATE);
                return true;
            }
        } catch (Exception e) {
            log.error(e);
        }

        throw new OperationFailedException(Subject.class.getName(), OPERATION_UPDATE);
    }

    public Subject getSubject(Subject subject) throws EntityNotFoundException {
        List<Subject> subjects = entityManager.createQuery(FIND_SUBJECT).setParameter(NAME, subject.getName())
                .setParameter(DESCRIPTION, subject.getDescription()).getResultList();
        if (subjects.size() != 0) {
            return subjects.get(0);
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        throw new EntityNotFoundException(Subject.class.getName());
    }

    public Subject getSubjectById(long id) throws EntityNotFoundException {
        Subject subject = entityManager.find(Subject.class, id);
        if (subject != null) {
            return subject;
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        throw new EntityNotFoundException(Subject.class.getName());
    }

    public List<Subject> getAllSubjects(int from, int quantity) {
        return entityManager.createQuery(GET_ALL_SUBJECTS).setFirstResult(from).setMaxResults(quantity).getResultList();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
