package com.easy.ecomm.resources;

import com.easy.ecomm.model.User;
import com.easy.ecomm.model.dto.UserDto;
import com.easy.ecomm.service.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UserResources {

    @Inject
    UserService userService;

    @POST
    @Transactional
    @Operation(summary = "Creates a new User")
    @APIResponse(responseCode = "201", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserDto.class, required = true)))
    public Response saveUser(@Valid UserDto user){
        return Response.status(Response.Status.CREATED)
                .entity(userService.saveUser(user, user.getPassword())).build();
    }

    @GET
    @Operation(summary = "Retrieve all Users")
    @APIResponse(responseCode = "200", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserDto.class)))
    public Iterable<User> findAll(){
        return userService.findAll();
    }

    @GET
    @Operation(summary = "Retrieve a User by its id")
    @APIResponse(responseCode = "200", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserDto.class)))
    @Path("{id}")
    public User findByUserById(@PathParam("id") int id){
        return userService.findUserById(id);
    }

    @GET
    @Operation(summary = "Retrieve a user by its email")
    @APIResponse(responseCode = "200", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserDto.class)))
    @Path("email/{email}")
    public User findUserByEmail(@PathParam("email") String email){
        return userService.finderByEmail(email);
    }

}
