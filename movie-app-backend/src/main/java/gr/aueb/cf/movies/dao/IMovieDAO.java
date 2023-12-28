package gr.aueb.cf.movies.dao;

import java.util.List;
import gr.aueb.cf.movies.model.Movie;
import gr.aueb.cf.movies.model.User;
import jakarta.persistence.EntityManager;

public interface IMovieDAO {

    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie);

    void deleteMovie(Long id);

    Movie getMovieById(Long id);

    List<Movie> getAllMovies();

    List<Movie> getMoviesByDirector(String director);

    List<Movie> getMoviesByUsername(String username);

    List<Movie> getMoviesByTitle(String title);

    Movie getMovieByTitle(String title);
}

