package gr.aueb.cf.movies.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Movie entity in the movie application.
 * <p>
 * The Movie class is an entity class that maps to the "MOVIES" table in the database.
 * It encapsulates data and behavior associated with a movie, including its title, director, and associated users.
 * Movies can have a many-to-many relationship with users, meaning a movie can be associated with multiple users,
 * and a user can be associated with multiple movies.
 * <p>
 * This class provides methods to get and set movie information, as well as manage associated users.
 * It also implements basic object methods such as equals and toString.
 *
 * @version 1.0
 */
@Entity
@Table(name = "MOVIES")
public class Movie {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TITLE", length = 100, nullable = false)
    private String title;

    @Column(name = "DIRECTOR", length = 50, nullable = false)
    private String director;

    /**
     * List of users associated with the movie.
     * <p>
     * This field represents the list of users who are associated with the movie.
     * It is used in a many-to-many relationship, where each movie can be associated with multiple users,
     * and each user can be associated with multiple movies.
     * The association is mapped by the "movies" field in the User class.
     * The list is fetched eagerly to ensure that the associated users are loaded when the movie is loaded,
     * and it cascades the operations of persist, merge, and remove to the associated users.
     */
    @ManyToMany(mappedBy = "movies", fetch = FetchType.EAGER , cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private List<User> users = new ArrayList<>();

    public Movie() {}

    public Movie(String title, String director) {
        this.title = title;
        this.director = director;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return title.equals(movie.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                '}';
    }
}

