package gr.aueb.cf.movies.dao;

import gr.aueb.cf.movies.model.Movie;
import gr.aueb.cf.movies.model.User;

import java.util.List;

public interface IUserDAO {

    User insert(User user);

    User update(User user);

    void delete(Long id);

    List<User> getUsersByUsername(String username);

    User getByUsername(String username);

    User getById(Long id);

    void addMovie(User user, Movie movie);

    void removeMovie(User user, Movie movie);

    List<Movie> getAllMoviesByUsername(String username);

    User authenticate(String username, String password);
}
