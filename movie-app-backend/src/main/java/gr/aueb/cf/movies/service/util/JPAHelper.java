package gr.aueb.cf.movies.service.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * JPAHelper is a utility class for managing Java Persistence API (JPA)
 * related operations such as obtaining an EntityManagerFactory, creating
 * and managing EntityManager instances, and handling transactions.
 * This class follows the Singleton pattern and provides thread-local
 * EntityManager instances to ensure thread-safety.
 */
public class JPAHelper {
    private static EntityManagerFactory emf; // The EntityManagerFactory instance
    private static ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>(); // Thread-local EntityManager instance

    private JPAHelper() {} // Private constructor to prevent instantiation

    /**
     * Returns the EntityManagerFactory instance. If the instance is not
     * created or closed, a new instance will be created using the
     * "usersPU" persistence unit name.
     *
     * @return The EntityManagerFactory instance.
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        if ((emf == null) || ((!emf.isOpen()))) {
            emf = Persistence.createEntityManagerFactory("usersPU");
        }
        return emf;
    }

    /**
     * Returns the thread-local EntityManager instance. If the instance is
     * not created or closed, a new instance will be created using the
     * EntityManagerFactory obtained from {@link #getEntityManagerFactory()}.
     *
     * @return The thread-local EntityManager instance.
     */
    public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();
        if ((em == null) || (!em.isOpen())) {
            em = getEntityManagerFactory().createEntityManager();
            threadLocal.set(em);
        }
        return em;
    }

    /**
     * Closes the thread-local EntityManager instance.
     */
    public static void closeEntityManager() {
        getEntityManager().close();
    }

    /**
     * Begins a new transaction on the thread-local EntityManager instance.
     */
    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    /**
     * Commits the current transaction on the thread-local EntityManager instance.
     */
    public static void commitTransaction() {
        getEntityManager().getTransaction().commit();
    }

    /**
     * Rolls back the current transaction on the thread-local EntityManager instance.
     */
    public static void rollbackTransaction() {
        getEntityManager().getTransaction().rollback();
    }

    /**
     * Closes the EntityManagerFactory instance.
     */
    public static void closeEMF() {
        emf.close();
    }
}
