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
import smthelusive.dto.request.GenreRequestDTO;
import smthelusive.exception.GenreNotFoundException;
import smthelusive.service.GenreService;

import java.net.URI;

@Path(GenreResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreResource {
    public static final String RESOURCE_PATH = "/api/v1/genres";
    @Inject
    private GenreService genreService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","user"})
    @APIResponse(responseCode = "200")
    public Response getGenres() {
        return Response.ok(genreService.getAll()).build();
    }

    @GET
    @Path("{genreId}")
    @RolesAllowed({"admin","user"})
    @APIResponses({@APIResponse(responseCode = "200"), @APIResponse(responseCode = "404")})
    public Response getSingleGenre(@PathParam("genreId") long id) throws GenreNotFoundException {
        return Response.ok(genreService.getSingleGenre(id)).build();
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    @APIResponses({@APIResponse(responseCode = "400"), @APIResponse(responseCode = "201")})
    public Response createGenre(@Valid GenreRequestDTO genreRequestDTO) {
        return Response.created(URI.create(String.format("%s/%s", RESOURCE_PATH, genreService.create(genreRequestDTO)))).build();

    }

    @PUT
    @Path("{genreId}")
    @Transactional
    @RolesAllowed("admin")
    @APIResponses({@APIResponse(responseCode = "400"), @APIResponse(responseCode = "404"), @APIResponse(responseCode = "200")})
    public Response updateGenre(@PathParam("genreId") long id, @Valid GenreRequestDTO genreRequestDTO) throws GenreNotFoundException {
        return Response.ok(genreService.update(id, genreRequestDTO)).build();
    }

    @DELETE
    @Path("{genreId}")
    @Transactional
    @RolesAllowed("admin")
    @APIResponses({@APIResponse(responseCode = "404"), @APIResponse(responseCode = "200")})
    public Response deleteGenre(@PathParam("genreId") long id) throws GenreNotFoundException {
        genreService.delete(id);
        return Response.ok().build();
    }
}
