package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.services.ITeacherService;
import week4.home.study.entity.Teacher;

import javax.persistence.EntityManager;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class TeacherServiceImpl implements ITeacherService {
    private static final String UPDATE_TEACHER =
            "UPDATE Teacher c set c.name=:name, c.experience=:experience, subject_id=:subject_id " +
                    "WHERE teacher_id LIKE :teacher_id";
    private static final String FIND_TEACHER = "SELECT s FROM Teacher s WHERE s.name LIKE :name AND " +
            "s.experience LIKE :experience AND subject_id LIKE :subject_id";
    private static final String GET_MORE_THREE_YEARS_EXPERIENCED = "SELECT t from Teacher t where t.experience > 3";
    private static final String GET_MIN_EXPERIENCED = "select t from Teacher WHERE t.experience=" +
            "(SELECT MIN(experience) FROM Teacher);";
    private static final String GET_MAX_EXPERIENCED = "select t from Teacher t WHERE t.experience=" +
            "(SELECT MAX(experience) FROM Teacher);";
    private static final String GET_ALL_TEACHERS = "SELECT g from Teacher g";

    private static Logger log = Logger.getLogger(TeacherServiceImpl.class.getName());
    private static EntityManager entityManager;

    static {
        entityManager = HibernateUtil.getEm();
    }

    public boolean addTeacher(Teacher teacher) {
        try {
            if (getTeacher(teacher) == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(teacher);
                entityManager.getTransaction().commit();
                log.info("" + teacher.toString() + LOG_OPERATION_ADD);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("");
            //entityManager.getTransaction().rollback();
        }

        return false;
    }

    public boolean removeTeacher(long id) {
        Teacher teacher = getTeacherById(id);

        if (teacher != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(teacher);
            entityManager.getTransaction().commit();
            log.info(teacher.toString() + LOG_OPERATION_REMOVE + "");
            return true;
        }

        return false;
    }

    public boolean updateTeacher(Teacher teacher) {
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
            log.info("");
        }

        return false;
    }

    public Teacher getTeacher(Teacher teacher) {
        List<Teacher> teachers = entityManager.createQuery(FIND_TEACHER).setParameter(NAME, teacher.getName())
                .setParameter(EXPERIENCE, teacher.getExperience()).setParameter(SUBJECT_ID, teacher.getSubject().getId())
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

    public List<Teacher> getAllTeachers() {
        return entityManager.createQuery(GET_ALL_TEACHERS).setMaxResults(100).getResultList();
    }

    public List<Teacher> getTeacherByExperience() {
        return entityManager.createQuery(GET_MORE_THREE_YEARS_EXPERIENCED).setMaxResults(100).getResultList();
    }

    public List<Teacher> getMinExperienced() {
        return entityManager.createQuery(GET_MIN_EXPERIENCED).setMaxResults(100).getResultList();
    }

    public List<Teacher> getMaxExperienced() {
        return entityManager.createQuery(GET_MAX_EXPERIENCED).setMaxResults(100).getResultList();
    }

    public static void setEntityManager(EntityManager entityManager) {
        TeacherServiceImpl.entityManager = entityManager;
    }
}
