package gr.aueb.cf.movies.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class OmdbApiService {
    private static final String OMDB_URL = "http://www.omdbapi.com/";
    private static final String OMDB_API_KEY = "API_KEY";

    public String getMovieDetails(String movieTitle) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(OMDB_URL)
                .queryParam("apikey", OMDB_API_KEY)
                .queryParam("t", movieTitle)
                .queryParam("plot", "full");
        String response = target.request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
}
