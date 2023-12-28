package gr.aueb.cf.movies.rest;

import gr.aueb.cf.movies.model.Movie;
import gr.aueb.cf.movies.model.User;
import gr.aueb.cf.movies.service.IUserService;
import gr.aueb.cf.movies.service.exceptions.EntityNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/movies")
public class AddMovieToWatchlistController {

    @Inject
    private IUserService userService;

    @POST
    @Path("/watchlist/{username}/{title}/{director}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovieToWatchlist(
            @PathParam("username") String username,
            @PathParam("title") String title,
            @PathParam("director") String director) throws EntityNotFoundException {

        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Movie movie = new Movie(title, director);

        User updatedUser = userService.getUserById(user.getId());
        updatedUser = userService.addMovieToWatchlist(username,movie);

        return Response.status(Response.Status.OK).build();
    }
}