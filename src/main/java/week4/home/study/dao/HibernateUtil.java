package week4.home.study.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static final String PERSISTENT_UNIT_NAME = "main";
    private static final String TEST_PERSISTENT_UNIT_NAME = "test";

    private static final EntityManagerFactory emf;
    private static final EntityManagerFactory testEmf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
            testEmf = Persistence.createEntityManagerFactory(TEST_PERSISTENT_UNIT_NAME);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEm() {
        return emf.createEntityManager();
    }

    public static EntityManager getTestEm() {
        return testEmf.createEntityManager();
    }
}
