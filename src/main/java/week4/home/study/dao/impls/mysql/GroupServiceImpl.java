package week4.home.study.dao.impls.mysql;

import org.apache.log4j.Logger;
import week4.home.study.dao.HibernateUtil;
import week4.home.study.dao.services.IGroupService;
import week4.home.study.entity.Groups;

import javax.persistence.EntityManager;
import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

public class GroupServiceImpl implements IGroupService {
    private static final String UPDATE_GROUP =
            "UPDATE Groups c set c.name=:name WHERE group_id LIKE :group_id";
    private static final String FIND_GROUP = "SELECT g FROM Groups g WHERE g.name LIKE :name";
    private static final String GET_ALL_GROUPS = "SELECT g from Groups g";

    private static Logger log = Logger.getLogger(GroupServiceImpl.class.getName());
    private static EntityManager entityManager;

    static {
        entityManager = HibernateUtil.getEm();
    }

    public boolean addGroup(Groups groups) {
        try {
            if (getGroup(groups) == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(groups);
                entityManager.getTransaction().commit();
                log.info(groups.toString() + LOG_OPERATION_ADD);
                return true;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.info("");
            //entityManager.getTransaction().rollback();
        }

        return false;
    }

    public boolean removeGroup(long id) {
        Groups groups = getGroupById(id);

        if (groups != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(groups);
            entityManager.getTransaction().commit();
            log.info(groups.toString() + " " + LOG_OPERATION_REMOVE);
            return true;
        }

        return false;
    }

    public boolean updateGroup(Groups groups) {
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
            log.info("");
        }

        return false;
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

    public List<Groups> getAllGroups() {
        return entityManager.createQuery(GET_ALL_GROUPS).setMaxResults(100).getResultList();
    }

    public static void setEntityManager(EntityManager entityManager) {
        GroupServiceImpl.entityManager = entityManager;
    }
}
