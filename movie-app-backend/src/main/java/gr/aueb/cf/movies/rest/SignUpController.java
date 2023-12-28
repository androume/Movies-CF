package gr.aueb.cf.movies.rest;

import gr.aueb.cf.movies.dto.UserDTO;
import gr.aueb.cf.movies.model.User;
import gr.aueb.cf.movies.service.IUserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Path("/signup")
public class SignUpController {

    @Inject
    IUserService userService;

    @POST
    @Path("/create")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(MultivaluedMap<String, String> formParams) {

        String username = formParams.getFirst("username");
        String password = formParams.getFirst("password");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        User newUser = userService.insertUser(userDTO);

        return Response.status(Response.Status.CREATED).build();
    }
}
