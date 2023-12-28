package gr.aueb.cf.movies.dao;

import gr.aueb.cf.movies.model.Movie;
import gr.aueb.cf.movies.model.User;
import gr.aueb.cf.movies.service.util.JPAHelper;
import jakarta.persistence.*;

import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class UserDAOImpl implements IUserDAO {
    @Override
    public User insert(User user) {
        EntityManager em = getEntityManager();
        em.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        EntityManager em = getEntityManager();
        getEntityManager().merge(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        User userToDelete = em.find(User.class,id);
        em.remove(userToDelete);
    }

    @Override
    public List<User> getUsersByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username LIKE :username";
        TypedQuery<User> query = getEntityManager().createQuery(jpql, User.class);
        query.setParameter("username", username + "%");
        return query.getResultList();
    }

    @Override
    public User getByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = getEntityManager().createQuery(jpql, User.class);
        query.setParameter("username", username);
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public User getById(Long id) {
        EntityManager em = getEntityManager();
        return em.find(User.class,id);
    }

    @Override
    public void addMovie(User user, Movie movie) {
        EntityManager em = getEntityManager();
        user.addMovie(movie);
        em.merge(user);
    }

    @Override
    public void removeMovie(User user, Movie movie) {
        EntityManager em = getEntityManager();
        user.removeMovie(movie);
        em.merge(user);
    }

    @Override
    public List<Movie> getAllMoviesByUsername(String username) {
        EntityManager em = getEntityManager();
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m JOIN m.users u WHERE u.username = :username", Movie.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

    @Override
    public User authenticate(String username, String password) {
        EntityManager em = JPAHelper.getEntityManager();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
