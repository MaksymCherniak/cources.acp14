package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.services.ISubjectService;
import week4.home.study.entity.Subject;

import javax.persistence.EntityManager;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class SubjectServiceImpl implements ISubjectService {
    private static final String UPDATE_SUBJECT =
            "UPDATE Subject c set c.name=:name, c.description=:description WHERE subject_id LIKE :subject_id";
    private static final String FIND_SUBJECT = "SELECT s FROM Subject s WHERE s.name LIKE :name AND s.description LIKE :description";
    private static final String GET_ALL_SUBJECTS = "SELECT g from Subject g";

    private static Logger log = Logger.getLogger(SubjectServiceImpl.class.getName());
    private static EntityManager entityManager;

    static {
        entityManager = HibernateUtil.getEm();
    }

    public boolean addSubject(Subject subject) {
        try {
            if (getSubject(subject) == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(subject);
                entityManager.getTransaction().commit();
                log.info(subject.toString() + LOG_OPERATION_ADD + " ");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("");
           // entityManager.getTransaction().rollback();
        }

        return false;
    }

    public boolean removeSubject(long id) {
        Subject subject = getSubjectById(id);

        if (subject != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(subject);
            entityManager.getTransaction().commit();
            log.info("" + subject.toString() + LOG_OPERATION_REMOVE);
            return true;
        }

        return false;
    }

    public boolean updateSubject(Subject subject) {
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
            log.info("");
        }

        return false;
    }

    public Subject getSubject(Subject subject) {
        List<Subject> subjects = entityManager.createQuery(FIND_SUBJECT).setParameter(NAME, subject.getName())
                .setParameter(DESCRIPTION, subject.getDescription()).getResultList();
        if (subjects.size() != 0) {
            return subjects.get(0);
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        return null;
    }

    public Subject getSubjectById(long id) {
        Subject subject = entityManager.find(Subject.class, id);
        if (subject != null) {
            return subject;
        }

        log.info(ERROR_SUBJECT_NOT_FOUND);
        return null;
    }

    public List<Subject> getAllSubjects() {
        return entityManager.createQuery(GET_ALL_SUBJECTS).setMaxResults(100).getResultList();
    }

    public static void setEntityManager(EntityManager entityManager) {
        SubjectServiceImpl.entityManager = entityManager;
    }
}
