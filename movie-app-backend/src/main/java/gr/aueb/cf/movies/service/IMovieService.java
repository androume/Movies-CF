package gr.aueb.cf.movies.service;

import gr.aueb.cf.movies.model.Movie;

import java.util.List;

public interface IMovieService {

    List<Movie> getMoviesByTitle(String title);

    Movie getMovieByTitle(String title);

    List<Movie> getMoviesByUsername(String username);
}
