package smthelusive.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import smthelusive.dto.request.AuthorRequestDTO;
import smthelusive.exceptions.AuthorNotFoundException;
import smthelusive.service.AuthorService;

@Path(AuthorResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
    public Response getSingleAuthor(@PathParam("authorId") long id) {
        return authorService.getSingleAuthor(id).map(author -> Response.ok(author).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    @POST
    @Path("/create")
    @Transactional
    @RolesAllowed("admin")
    public Response createAuthor(AuthorRequestDTO authorRequestDTO) {
        return authorService.create(authorRequestDTO).map(author -> Response.ok(author).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT // todo put or post?
    @Path("update/{authorId}")
    @Transactional
    @RolesAllowed("admin")
    public Response updateAuthor(@PathParam("authorId") long id, AuthorRequestDTO authorRequestDTO) {
        try {
            return authorService.update(id, authorRequestDTO)
                    .map(responseDTO -> Response.ok(responseDTO).build())
                    .orElse(Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                            "author doesn't exist").build());
        } catch (AuthorNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                    e.getMessage()).build(); // todo status comes without a message
        }
    }

    @DELETE
    @Path("delete/{authorId}")
    @Transactional
    @RolesAllowed("admin")
    public Response deleteAuthor(@PathParam("authorId") long id) {
        return authorService.delete(id) ?
                Response.ok().build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }
}
