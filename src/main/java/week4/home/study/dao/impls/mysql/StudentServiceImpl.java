package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.services.IStudentService;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;

import javax.persistence.EntityManager;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class StudentServiceImpl implements IStudentService {
    private static final String UPDATE_STUDENT =
            "UPDATE Student c set c.name=:name, group_id=:group_id WHERE student_id LIKE :student_id";
    private static final String FIND_STUDENT = "SELECT s FROM Student s WHERE s.name LIKE :name AND group_id LIKE :group_id";
    private static final String GET_STUDENTS_BY_GROUP = "SELECT s FROM Student s WHERE group_id LIKE :group_id";
    private static final String GET_ALL_STUDENTS = "SELECT g from Student g";

    private static Logger log = Logger.getLogger(StudentServiceImpl.class.getName());
    private static EntityManager entityManager;

    static {
        entityManager = HibernateUtil.getEm();
    }

    public boolean addStudent(Student student) {
        try {
            if (getStudent(student) == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(student);
                entityManager.getTransaction().commit();
                return true;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.info("");
        }

        return false;
    }

    public boolean removeStudent(long id) {
        Student student = getStudentById(id);

        if (student != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(student);
            entityManager.getTransaction().commit();
            log.info(student.toString() + LOG_OPERATION_REMOVE);
            return true;
        }

        return false;
    }

    public boolean updateStudent(Student student) {
        try {
            if (getStudentById(student.getId()) != null) {
                entityManager.getTransaction().begin();
                entityManager.createQuery(UPDATE_STUDENT)
                        .setParameter(NAME, student.getName())
                        .setParameter(GROUP_ID, student.getGroups().getId())
                        .setParameter(STUDENT_ID, student.getId()).executeUpdate();
                entityManager.getTransaction().commit();
                entityManager.clear();
                log.info(student.toString() + LOG_OPERATION_UPDATE);
                return true;
            }
        } catch (Exception e) {
            log.info("");
        }

        return false;
    }

    public Student getStudentById(long id) {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            return student;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        return null;
    }

    public Student getStudent(Student student) {
        List<Student> students = entityManager.createQuery(FIND_STUDENT)
                .setParameter(NAME, student.getName())
                .setParameter(GROUP_ID, student.getGroups().getId()).getResultList();
        if (students.size() != 0) {
            return students.get(0);
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        return null;
    }


    public List<Student> getAllStudents() {
        return entityManager.createQuery(GET_ALL_STUDENTS).setMaxResults(100).getResultList();
    }

    public List<Student> getStudentsByGroup(Groups groups) {
        List<Student> students = entityManager.createQuery(GET_STUDENTS_BY_GROUP)
                .setParameter(GROUP_ID, groups.getId()).getResultList();
        if (students != null) {
            return students;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        return null;
    }

    public static void setEntityManager(EntityManager entityManager) {
        StudentServiceImpl.entityManager = entityManager;
    }
}
