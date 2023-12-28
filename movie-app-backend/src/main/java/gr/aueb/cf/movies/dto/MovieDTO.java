package gr.aueb.cf.movies.dto;

import java.util.ArrayList;
import java.util.List;

public class MovieDTO {

    private Long id;
    private String title;
    private String director;
    private List<UserDTO> users = new ArrayList<>();

    public MovieDTO() {}

    public MovieDTO(Long id, String title, String director) {
        this.id = id;
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

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}

