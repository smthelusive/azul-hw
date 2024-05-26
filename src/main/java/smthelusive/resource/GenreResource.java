package smthelusive.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import smthelusive.dto.request.GenreRequestDTO;
import smthelusive.exceptions.GenreNotFoundException;
import smthelusive.service.GenreService;

@Path(GenreResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreResource {
    public static final String RESOURCE_PATH = "/api/v1/genres";
    @Inject
    private GenreService genreService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenres() {
        return Response.ok(genreService.getAll()).build();
    }

    @GET
    @Path("{genreId}")
    public Response getSingleGenre(@PathParam("genreId") long id) {
        return genreService.getSingleGenre(id).map(genre -> Response.ok(genre).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    @POST
    @Path("/create")
    @Transactional
    public Response createGenre(GenreRequestDTO genreRequestDTO) {
        return genreService.create(genreRequestDTO).map(genre -> Response.ok(genre).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT // todo put or post?
    @Path("update/{genreId}")
    @Transactional
    public Response updateGenre(@PathParam("genreId") long id, GenreRequestDTO genreRequestDTO) {
        try {
            return genreService.update(id, genreRequestDTO)
                    .map(responseDTO -> Response.ok(responseDTO).build())
                    .orElse(Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                            "genre doesn't exist").build());
        } catch (GenreNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                    e.getMessage()).build(); // todo status comes without a message
        }
    }

    @DELETE
    @Path("delete/{genreId}")
    @Transactional
    public Response deleteGenre(@PathParam("genreId") long id) {
        return genreService.delete(id) ?
                Response.ok().build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }
}
