package gr.aueb.cf.movies.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user entity in the movie application.
 * <p>
 * The User class is an entity class that maps to the "USERS" table in the database.
 * It stores information about a user, including their username, password, and movies they are associated with.
 * Users can have a many-to-many relationship with movies, meaning a user can have multiple movies, and a movie can have multiple users.
 * <p>
 * This class provides methods to get and set user information, as well as manage associated movies.
 * It also implements basic object methods such as equals and toString.
 *
 * @version 1.0
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 50, nullable = false)
    private String password;

    /**
     * List of movies associated with the user.
     * <p>
     * This field represents the list of movies that are associated with the user.
     * It is used in a many-to-many relationship, where each user can have multiple movies,
     * and each movie can be associated with multiple users.
     * The association is mapped by the "movies" field in the Movie class.
     * The list is fetched eagerly to ensure that the associated movies are loaded when the user is loaded,
     * and it cascades the operations of persist, merge, and remove to the associated movies.
     * Movies are added to this list using the {@link #addMovie(Movie)} method, and removed using the {@link #removeMovie(Movie)} method.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(name = "USER_MOVIE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "MOVIE_ID"))
    private List<Movie> movies = new ArrayList<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Movie> getMovies() {
        return movies;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        movie.getUsers().add(this);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
        movie.getUsers().remove(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
