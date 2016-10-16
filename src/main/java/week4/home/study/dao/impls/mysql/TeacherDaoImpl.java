package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import javax.persistence.EntityManager;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class TeacherDaoImpl implements ITeacherDao {
    private static final String UPDATE_TEACHER =
            "UPDATE Teacher c set c.name=:name, c.experience=:experience, subject_id=:subject_id " +
                    "WHERE teacher_id LIKE :teacher_id";
    private static final String FIND_TEACHER = "SELECT s FROM Teacher s WHERE s.name LIKE :name AND " +
            "s.experience LIKE :experience AND subject_id LIKE :subject_id";
    private static final String GET_MORE_THREE_YEARS_EXPERIENCED = "SELECT t from Teacher t where t.experience > :experience";
    private static final String GET_MIN_EXPERIENCED = "select t from Teacher t WHERE t.experience=" +
            "(SELECT min(experience) FROM Teacher)";
    private static final String GET_MAX_EXPERIENCED = "select t from Teacher t WHERE t.experience=" +
            "(SELECT max(experience) FROM Teacher)";
    private static final String GET_ALL_TEACHERS = "SELECT g from Teacher g";

    private static Logger log = Logger.getLogger(TeacherDaoImpl.class.getName());
    private EntityManager entityManager;

    public TeacherDaoImpl() {
        entityManager = HibernateUtil.getEm();
    }

    public TeacherDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean addTeacher(Teacher teacher) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        if (teacher == null) {
            throw new ComingNullObjectException(Teacher.class.getName(), OPERATION_ADD);
        }

        try {
            if (getTeacher(teacher) == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(teacher);
                entityManager.getTransaction().commit();
                log.info("" + teacher.toString() + LOG_OPERATION_ADD);
                return true;
            } else {
                throw new EntityAlreadyExistException(teacher);
            }
        } catch (RuntimeException e) {
            log.error(e);
        }

        throw new OperationFailedException(Teacher.class.getName(), OPERATION_ADD);
    }

    public boolean removeTeacher(long id) throws EntityNotFoundException {
        Teacher teacher = getTeacherById(id);

        if (teacher != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(teacher);
            entityManager.getTransaction().commit();
            log.info(teacher.toString() + LOG_OPERATION_REMOVE + "");
            return true;
        }

        throw new EntityNotFoundException(Teacher.class.getName());
    }

    public boolean updateTeacher(Teacher teacher) throws ComingNullObjectException, OperationFailedException {
        if (teacher == null) {
            throw new ComingNullObjectException(Teacher.class.getName(), OPERATION_UPDATE);
        }

        try {
            if (getTeacherById(teacher.getId()) != null) {
                entityManager.getTransaction().begin();
                entityManager.createQuery(UPDATE_TEACHER)
                        .setParameter(NAME, teacher.getName())
                        .setParameter(EXPERIENCE, teacher.getExperience())
                        .setParameter(SUBJECT_ID, teacher.getSubject().getId())
                        .setParameter(TEACHER_ID, teacher.getId()).executeUpdate();
                entityManager.getTransaction().commit();
                entityManager.clear();
                log.info(teacher.toString() + LOG_OPERATION_UPDATE);
                return true;
            }
        } catch (Exception e) {
            log.error(e);
        }

        throw new OperationFailedException(Teacher.class.getName(), OPERATION_UPDATE);
    }

    public Teacher getTeacher(Teacher teacher) {
        List<Teacher> teachers = entityManager.createQuery(FIND_TEACHER)
                .setParameter(NAME, teacher.getName())
                .setParameter(EXPERIENCE, teacher.getExperience())
                .setParameter(SUBJECT_ID, teacher.getSubject().getId())
                .getResultList();
        if (teachers.size() != 0) {
            return teachers.get(0);
        }

        log.info(ERROR_TEACHER_NOT_FOUND);
        return null;
    }

    public Teacher getTeacherById(long id) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        if (teacher != null) {
            return teacher;
        }

        log.info(ERROR_TEACHER_NOT_FOUND);
        return null;
    }

    public List<Teacher> getAllTeachers(int from, int quantity) {
        return entityManager.createQuery(GET_ALL_TEACHERS).setFirstResult(from).setMaxResults(quantity).getResultList();
    }

    public List<Teacher> getTeacherByExperience(int year, int from, int quantity) {
        return entityManager.createQuery(GET_MORE_THREE_YEARS_EXPERIENCED)
                .setParameter(EXPERIENCE, year).setFirstResult(from).setMaxResults(quantity).getResultList();
    }

    public List<Teacher> getMinExperiencedTeachers(int from, int quantity) {
        return entityManager.createQuery(GET_MIN_EXPERIENCED).setFirstResult(from).setMaxResults(quantity).getResultList();
    }


    public List<Teacher> getMaxExperiencedTeachers(int from, int quantity) {
        return entityManager.createQuery(GET_MAX_EXPERIENCED).setFirstResult(from).setMaxResults(quantity).getResultList();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
