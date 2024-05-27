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
import smthelusive.exceptions.GenreNotFoundException;
import smthelusive.service.GenreService;

@Path(GenreResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@APIResponse(responseCode = "200")
public class GenreResource {
    public static final String RESOURCE_PATH = "/api/v1/genres";
    @Inject
    private GenreService genreService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","user"})
    public Response getGenres() {
        return Response.ok(genreService.getAll()).build();
    }

    @GET
    @Path("{genreId}")
    @RolesAllowed({"admin","user"})
    @APIResponse(responseCode = "404")
    public Response getSingleGenre(@PathParam("genreId") long id) throws GenreNotFoundException {
        return Response.ok(genreService.getSingleGenre(id)).build();
    }

    @POST
    @Path("/create")
    @Transactional
    @RolesAllowed("admin")
    @APIResponse(responseCode = "400")
    public Response createGenre(@Valid GenreRequestDTO genreRequestDTO) {
        return Response.ok(genreService.create(genreRequestDTO)).build();
    }

    @PUT // todo put or post?
    @Path("update/{genreId}")
    @Transactional
    @RolesAllowed("admin")
    @APIResponses({@APIResponse(responseCode = "400"), @APIResponse(responseCode = "404")})
    public Response updateGenre(@PathParam("genreId") long id, @Valid GenreRequestDTO genreRequestDTO) throws GenreNotFoundException {
        return Response.ok(genreService.update(id, genreRequestDTO)).build();
    }

    @DELETE
    @Path("delete/{genreId}")
    @Transactional
    @RolesAllowed("admin")
    @APIResponse(responseCode = "404")
    public Response deleteGenre(@PathParam("genreId") long id) throws GenreNotFoundException {
        genreService.delete(id);
        return Response.ok().build();
    }
}
