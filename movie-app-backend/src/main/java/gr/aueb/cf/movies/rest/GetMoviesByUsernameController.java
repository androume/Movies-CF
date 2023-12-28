package gr.aueb.cf.movies.rest;

import gr.aueb.cf.movies.model.Movie;
import gr.aueb.cf.movies.service.IMovieService;

import javax.inject.Inject;
import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/user")
public class GetMoviesByUsernameController {

    @Inject
    private IMovieService movieService;

    @GET
    @Path("/watchlist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoviesByUsername(@QueryParam("username") String username) {
        if (username == null || username.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Username cannot be empty")
                    .build();
        }

        List<Movie> movies = movieService.getMoviesByUsername(username);

        if (movies == null || movies.isEmpty()) {
            return Response.noContent().build();
        } else {
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (Movie movie : movies) {
                JsonObject jsonObject = Json.createObjectBuilder()
                        .add("title", movie.getTitle())
                        .build();
                jsonArrayBuilder.add(jsonObject);
            }
            JsonArray jsonArray = jsonArrayBuilder.build();

            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("movies", jsonArray)
                    .build();

            Map<String, Boolean> config = new HashMap<>();
            config.put(JsonGenerator.PRETTY_PRINTING, false);

            StringWriter writer = new StringWriter();
            JsonWriterFactory writerFactory = Json.createWriterFactory(config);
            JsonWriter jsonWriter = writerFactory.createWriter(writer);
            jsonWriter.writeObject(jsonObject);
            jsonWriter.close();
            String json = writer.toString();

            return Response.status(Response.Status.OK).entity(json).build();
        }
    }
}