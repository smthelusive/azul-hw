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
import smthelusive.dto.request.BookRequestDTO;
import smthelusive.exceptions.BookNotFoundException;
import smthelusive.exceptions.InvalidReferenceException;
import smthelusive.service.BookService;

@Path(BookResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@APIResponse(responseCode = "200")
public class BookResource {
    public static final String RESOURCE_PATH = "/api/v1/books";
    @Inject
    private BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","user"})
    public Response getBooks(@BeanParam BookFilterParams bookFilterParams, @BeanParam PageParams pageParams) {
        return Response.ok(bookService.getBooksFiltered(bookFilterParams, pageParams)).build(); // todo Response.created
    }

    @GET
    @Path("{bookId}")
    @RolesAllowed({"admin","user"})
    @APIResponse(responseCode = "404")
    public Response getSingleBook(@PathParam("bookId") long id) throws BookNotFoundException {
        return Response.ok(bookService.getSingleBook(id)).build();
    }

    @POST
    @Path("/create")
    @Transactional
    @RolesAllowed("admin")
    @APIResponse(responseCode = "400")
    public Response createBook(@Valid BookRequestDTO bookRequestDTO) throws InvalidReferenceException {
        return Response.ok(bookService.create(bookRequestDTO)).build();

    }

    @PUT
    @Path("update/{bookId}")
    @Transactional
    @RolesAllowed("admin")
    @APIResponses({@APIResponse(responseCode = "400"), @APIResponse(responseCode = "404")})
    public Response updateBook(@PathParam("bookId") long id, @Valid BookRequestDTO bookRequestDTO)
            throws BookNotFoundException, InvalidReferenceException {
        return Response.ok(bookService.update(id, bookRequestDTO)).build();
    }

    @DELETE
    @Path("delete/{bookId}")
    @Transactional
    @RolesAllowed("admin")
    @APIResponse(responseCode = "404")
    public Response deleteBook(@PathParam("bookId") long id) throws BookNotFoundException {
        bookService.delete(id);
        return Response.ok().build();
    }
}
