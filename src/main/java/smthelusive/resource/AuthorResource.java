package smthelusive.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import smthelusive.dto.request.AuthorRequestDTO;
import smthelusive.exceptions.AuthorNotFoundException;
import smthelusive.service.AuthorService;

@Path(AuthorResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@APIResponse(responseCode = "200")
public class AuthorResource {
    public static final String RESOURCE_PATH = "/api/v1/authors";

    @Inject
    private AuthorService authorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","user"})
    public Response getAuthors() {
        return Response.ok(authorService.getAll()).build();
    }

    @GET
    @Path("{authorId}")
    @RolesAllowed({"admin","user"})
    @APIResponse(responseCode = "404")
    public Response getSingleAuthor(@PathParam("authorId") long id) throws AuthorNotFoundException {
        return Response.ok(authorService.getSingleAuthor(id)).build();
    }
    @POST
    @Transactional
    @RolesAllowed("admin")
    @APIResponse(responseCode = "400")
    public Response createAuthor(@Valid AuthorRequestDTO authorRequestDTO) {
        return Response.ok(authorService.create(authorRequestDTO)).build();
    }

    @PUT
    @Path("{authorId}")
    @Transactional
    @RolesAllowed("admin")
    @APIResponses({@APIResponse(responseCode = "400"), @APIResponse(responseCode = "404")})
    public Response updateAuthor(@PathParam("authorId") long id, @Valid AuthorRequestDTO authorRequestDTO) throws AuthorNotFoundException {
        return Response.ok(authorService.update(id, authorRequestDTO)).build();
    }

    @DELETE
    @Path("{authorId}")
    @Transactional
    @RolesAllowed("admin")
    @APIResponse(responseCode = "404")
    public Response deleteAuthor(@PathParam("authorId") long id) throws AuthorNotFoundException {
        authorService.delete(id);
        return Response.ok().build();
    }
}
