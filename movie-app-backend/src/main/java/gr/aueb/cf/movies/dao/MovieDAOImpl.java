package gr.aueb.cf.movies.dao;

import gr.aueb.cf.movies.model.Movie;
import gr.aueb.cf.movies.service.util.JPAHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class MovieDAOImpl implements IMovieDAO {

    @Override
    public Movie addMovie(Movie movie) {
        EntityManager em = getEntityManager();
        em.persist(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(Movie movie) {
        EntityManager em = getEntityManager();
        em.merge(movie);
        return movie;
    }

    @Override
    public void deleteMovie(Long id) {
        EntityManager em = getEntityManager();
        Movie movie = em.find(Movie.class, id);
        em.remove(movie);
    }

    @Override
    public Movie getMovieById(Long id) {
        EntityManager em = getEntityManager();
        return em.find(Movie.class, id);
    }

    @Override
    public List<Movie> getAllMovies() {
        String jpql = "SELECT m FROM Movie m";
        TypedQuery<Movie> query = getEntityManager().createQuery(jpql, Movie.class);
        return query.getResultList();
    }

    @Override
    public List<Movie> getMoviesByDirector(String director) {
        String jpql = "SELECT m FROM Movie m WHERE m.director = :director";
        TypedQuery<Movie> query = getEntityManager().createQuery(jpql, Movie.class);
        query.setParameter("genre", director);
        return query.getResultList();
    }

    @Override
    public List<Movie> getMoviesByUsername(String username) {
        EntityManager em = JPAHelper.getEntityManager();
        String jpql = "SELECT m FROM Movie m JOIN m.users u WHERE u.username = :username";
        TypedQuery<Movie> query = em.createQuery(jpql, Movie.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public List<Movie> getMoviesByTitle(String title) {
        String jpql = "SELECT m FROM Movie m WHERE m.title LIKE :title";
        TypedQuery<Movie> query = getEntityManager().createQuery(jpql, Movie.class);
        query.setParameter("title", title + "%");
        return query.getResultList();
    }

    @Override
    public Movie getMovieByTitle(String title) {
        String jpql = "SELECT m FROM Movie m WHERE m.title = :title";
        TypedQuery<Movie> query = getEntityManager().createQuery(jpql, Movie.class);
        query.setParameter("title", title);
        List<Movie> movies = query.getResultList();
        return movies.isEmpty() ? null : movies.get(0);
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }
}

