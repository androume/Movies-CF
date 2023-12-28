package gr.aueb.cf.movies.service;

import gr.aueb.cf.movies.dao.IMovieDAO;
import gr.aueb.cf.movies.model.Movie;
import gr.aueb.cf.movies.service.util.JPAHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class MovieServiceImpl implements IMovieService {

    @Inject
    IMovieDAO movieDAO;

    @Override
    public List<Movie> getMoviesByTitle(String title) {
        EntityManager entityManager = JPAHelper.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Movie> query = entityManager.createQuery("SELECT m FROM Movie m WHERE m.title LIKE :title", Movie.class);
            query.setParameter("title", "%" + title + "%");
            List<Movie> movies = query.getResultList();
            transaction.commit();
            return movies;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public Movie getMovieByTitle(String title) {
        EntityManager entityManager = JPAHelper.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Movie movie = movieDAO.getMovieByTitle(title);
            transaction.commit();
            return movie;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Movie> getMoviesByUsername(String username) {
        EntityManager em = JPAHelper.getEntityManager();
        try {
            em.getTransaction().begin();

            List<Movie> movies = movieDAO.getMoviesByUsername(username);

            em.getTransaction().commit();

            return movies;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to get movies for username: " + username, e);
        } finally {
            em.close();
        }
    }
}
