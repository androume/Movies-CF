package gr.aueb.cf.movies;

import gr.aueb.cf.movies.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("usersPU");
        EntityManager em = emf.createEntityManager();

        // Create a new User entity and insert it into the database
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
