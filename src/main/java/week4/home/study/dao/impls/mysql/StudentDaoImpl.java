package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

@Service
public class StudentDaoImpl implements IStudentDao {
    private static final String UPDATE_STUDENT =
            "UPDATE Student c set c.name=:name, group_id=:group_id WHERE student_id LIKE :student_id";
    private static final String FIND_STUDENT = "SELECT s FROM Student s WHERE s.name LIKE :name AND group_id LIKE :group_id";
    private static final String GET_STUDENTS_BY_GROUP = "SELECT s FROM Student s WHERE group_id LIKE :group_id";
    private static final String GET_ALL_STUDENTS = "SELECT g from Student g";

    private static Logger log = Logger.getLogger(StudentDaoImpl.class.getName());
    private EntityManager entityManager;

    public StudentDaoImpl() {

    }

    public StudentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean addStudent(Student student) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException {
        if (student == null) {
            throw new ComingNullObjectException(Student.class.getName(), OPERATION_ADD);
        }

        try {
            if (getStudent(student) != null) {
                throw new EntityAlreadyExistException(student);
            }
        } catch (EntityNotFoundException e) {
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(student);
                entityManager.getTransaction().commit();
                log.info(" " + student.toString() + LOG_OPERATION_ADD);
                return true;
            } catch (RuntimeException ex) {
                log.error(e);
            }
        }

        throw new OperationFailedException(Student.class.getName(), OPERATION_ADD);
    }

    public boolean removeStudent(long id) throws EntityNotFoundException {
        Student student = getStudentById(id);

        entityManager.getTransaction().begin();
        entityManager.remove(student);
        entityManager.getTransaction().commit();
        log.info(student.toString() + LOG_OPERATION_REMOVE);
        return true;
    }

    public boolean updateStudent(Student student) throws ComingNullObjectException, OperationFailedException {
        if (student == null) {
            throw new ComingNullObjectException(Student.class.getName(), OPERATION_UPDATE);
        }

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
            log.error(e);
        }

        throw new OperationFailedException(Student.class.getName(), OPERATION_UPDATE);
    }

    public Student getStudentById(long id) throws EntityNotFoundException {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            return student;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        throw new EntityNotFoundException(Student.class.getName());
    }

    public Student getStudent(Student student) throws EntityNotFoundException {
        List<Student> students = entityManager.createQuery(FIND_STUDENT)
                .setParameter(NAME, student.getName())
                .setParameter(GROUP_ID, student.getGroups().getId()).getResultList();
        if (students.size() != 0) {
            return students.get(0);
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        throw new EntityNotFoundException(Student.class.getName());
    }


    public List<Student> getAllStudents(int from, int quantity) {
        return entityManager.createQuery(GET_ALL_STUDENTS).setFirstResult(from).setMaxResults(quantity).getResultList();
    }

    public List<Student> getStudentsByGroup(Groups groups, int quantity) {
        List<Student> students = entityManager.createQuery(GET_STUDENTS_BY_GROUP)
                .setParameter(GROUP_ID, groups.getId()).getResultList();
        if (students != null) {
            return students;
        }

        log.info(ERROR_STUDENT_NOT_FOUND);
        return null;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
