package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.entity.Groups;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import javax.persistence.EntityManager;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class GroupDaoImpl implements IGroupDao {
    private static final String UPDATE_GROUP =
            "UPDATE Groups c set c.name=:name WHERE group_id LIKE :group_id";
    private static final String FIND_GROUP = "SELECT g FROM Groups g WHERE g.name LIKE :name";
    private static final String GET_ALL_GROUPS = "SELECT g from Groups g";

    private static Logger log = Logger.getLogger(GroupDaoImpl.class.getName());
    private EntityManager entityManager;

    public GroupDaoImpl() {
        entityManager = HibernateUtil.getEm();
    }

    public GroupDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean addGroup(Groups groups) throws ComingNullObjectException, OperationFailedException, EntityAlreadyExistException{
        if (groups == null) {
            throw new ComingNullObjectException(Groups.class.getName(), OPERATION_ADD);
        }

        try {
            if (getGroup(groups) == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(groups);
                entityManager.getTransaction().commit();
                log.info(groups.toString() + LOG_OPERATION_ADD);
                return true;
            } else {
                throw new EntityAlreadyExistException(groups);
            }
        } catch (RuntimeException e) {
            log.error(e);
        }

        throw new OperationFailedException(Groups.class.getName(), OPERATION_ADD);
    }

    public boolean removeGroup(long id) throws EntityNotFoundException {
        Groups groups = getGroupById(id);

        if (groups != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(groups);
            entityManager.getTransaction().commit();
            log.info(groups.toString() + " " + LOG_OPERATION_REMOVE);
            return true;
        }

        throw new EntityNotFoundException(Groups.class.getName());
    }

    public boolean updateGroup(Groups groups) throws ComingNullObjectException, OperationFailedException {
        if (groups == null) {
            throw new ComingNullObjectException(Groups.class.getName(), OPERATION_UPDATE);
        }

        try {
            if (getGroupById(groups.getId()) != null) {
                entityManager.getTransaction().begin();
                entityManager.createQuery(UPDATE_GROUP)
                        .setParameter(NAME, groups.getName())
                        .setParameter(GROUP_ID, groups.getId()).executeUpdate();
                entityManager.getTransaction().commit();
                entityManager.clear();
                log.info(groups.toString() + LOG_OPERATION_UPDATE);
                return true;
            }
        } catch (Exception e) {
            log.error(e);
        }

        throw new OperationFailedException(Groups.class.getName(), OPERATION_UPDATE);
    }

    public Groups getGroup(Groups groups) {
        List<Groups> result = entityManager.createQuery(FIND_GROUP).setParameter(NAME, groups.getName()).getResultList();
        if (result.size() != 0) {
            return result.get(0);
        }

        log.info(ERROR_GROUP_NOT_FOUND);
        return null;
    }

    public Groups getGroupById(long id) {
        Groups groups = entityManager.find(Groups.class, id);
        if (groups != null) {
            return groups;
        }

        log.info(ERROR_GROUP_NOT_FOUND);
        return null;
    }

    public List<Groups> getAllGroups(int from, int quantity) {
        return entityManager.createQuery(GET_ALL_GROUPS).setFirstResult(from).setMaxResults(quantity).getResultList();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
