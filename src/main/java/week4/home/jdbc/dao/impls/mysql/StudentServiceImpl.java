package week4.home.jdbc.dao.impls.mysql;

import org.apache.log4j.Logger;
import week4.home.jdbc.dao.HibernateUtil;
import week4.home.jdbc.dao.services.IStudentService;
import week4.home.jdbc.entity.Group;
import week4.home.jdbc.entity.Student;

import javax.persistence.EntityManager;
import java.util.List;

public class StudentServiceImpl implements IStudentService {
    private static final String FIND_STUDENT = "SELECT s FROM students s WHERE s.name LIKE :name AND group_id LIKE :group_id";
    private static final String GET_STUDENTS_BY_GROUP = "SELECT s FROM students s WHERE group_id LIKE :group_id";
    private static final String NAME = "name";
    private static final String GROUP_ID = "group_id";

    private static Logger log = Logger.getLogger(StudentServiceImpl.class.getName());
    private static EntityManager entityManager;

    static {
        entityManager = HibernateUtil.getEm();
    }

    public boolean addStudent(Student student) {
        try {
            if (findStudent(student) == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(student);
                entityManager.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            log.info("");
            entityManager.getTransaction().rollback();
        }

        return false;
    }

    public boolean removeStudent(long id) {
        Student student = getStudentById(id);
        if (student != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(student);
            entityManager.getTransaction().commit();
            log.info("Student " + id + " removed");
            return true;
        } else {
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        return true;
    }

    public Student getStudentById(long id) {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            return student;
        } else {
            log.info("Student not found");
            return null;
        }
    }

    public Student findStudent(Student student) {
        Student result = (Student) entityManager.createQuery(FIND_STUDENT).setParameter(NAME, student.getName())
                  .setParameter(GROUP_ID, student.getGroup().getId()).getSingleResult();
        if (result != null) {
            return result;
        }

        log.info("Student not found");
        return null;
    }


    public List<Student> getAllStudents() {
        return entityManager.createNamedQuery("Student.getAll", Student.class).getResultList();
    }

    public List<Student> getStudentsByGroup(Group group) {
        List<Student> students = entityManager.createQuery(GET_STUDENTS_BY_GROUP)
                .setParameter(GROUP_ID, group.getId()).getResultList();
        if (students != null) {
            return students;
        }

        log.info("Student not found");
        return null;
    }

}
