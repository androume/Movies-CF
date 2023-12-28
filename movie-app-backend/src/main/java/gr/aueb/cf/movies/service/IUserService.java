package gr.aueb.cf.movies.service;

import gr.aueb.cf.movies.dto.UserDTO;
import gr.aueb.cf.movies.model.Movie;
import gr.aueb.cf.movies.model.User;
import gr.aueb.cf.movies.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IUserService {

    User insertUser(UserDTO userDTO);

    User updateUser(UserDTO userDTO) throws EntityNotFoundException;

    void deleteUser(Long id) throws EntityNotFoundException;

    User getUserById(Long id) throws EntityNotFoundException;

    List<User> getUsersByUsername(String username) throws EntityNotFoundException;

    User getUserByUsername(String username) throws EntityNotFoundException;

    User addMovieToWatchlist(String username, Movie movie);

    User removeMovieFromWatchlist(String username, Movie movie);

    User authenticateUser(String username, String password) throws EntityNotFoundException;
}
