package gr.aueb.cf.movies;

import gr.aueb.cf.movies.rest.*;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.FeatureContext;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ConfigApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(LoginController.class);
        classes.add(MovieController.class);
        classes.add(AddMovieToWatchlistController.class);
        classes.add(GetMoviesByUsernameController.class);
        classes.add(RemoveMovieFromWatchlistController.class);
        classes.add(SignUpController.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        singletons.add(new JacksonFeature());
        singletons.add(new CorsFeature());
        return singletons;
    }

    public static class CorsFeature implements DynamicFeature {

        @Override
        public void configure(ResourceInfo resourceInfo, FeatureContext context) {
            CorsFilter corsFilter = new CorsFilter();
            corsFilter.getAllowedOrigins().add("*");
            corsFilter.setAllowedHeaders("origin, content-type, accept, authorization");
            corsFilter.setAllowedMethods("GET, POST, PUT, DELETE, OPTIONS, HEAD");

            corsFilter.getAllowedOrigins().add("http://127.0.0.1:5500");
            corsFilter.setAllowCredentials(true);

            context.register(corsFilter);
        }
    }
}
