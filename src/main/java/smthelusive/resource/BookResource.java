package smthelusive.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import smthelusive.dto.request.BookRequestDTO;
import smthelusive.exceptions.AuthorNotFoundException;
import smthelusive.exceptions.BookNotFoundException;
import smthelusive.exceptions.GenreNotFoundException;
import smthelusive.service.BookService;

@Path(BookResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    public static final String RESOURCE_PATH = "/api/v1/books";
    @Inject
    private BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","user"})
    public Response getBooks(@BeanParam BookFilterParams bookFilterParams, @BeanParam PageParams pageParams) {
        return Response.ok(bookService.getBooksFiltered(bookFilterParams, pageParams)).build();
    }

    @GET
    @Path("{bookId}")
    @RolesAllowed({"admin","user"})
    public Response getSingleBook(@PathParam("bookId") long id) {
        return bookService.getSingleBook(id).map(book -> Response.ok(book).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Path("/create")
    @Transactional
    @RolesAllowed("admin")
    public Response createBook(BookRequestDTO bookRequestDTO) {
        try {
            return bookService.create(bookRequestDTO).map(book -> Response.ok(book).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (AuthorNotFoundException | GenreNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                    e.getMessage()).build();
        }
    }

    @PUT // todo put or post?
    @Path("update/{bookId}")
    @Transactional
    @RolesAllowed("admin")
    public Response updateBook(@PathParam("bookId") long id, BookRequestDTO bookRequestDTO) {
        // todo request dto validation for not empty list doesn't work
        try {
            return bookService.update(id, bookRequestDTO).map(responseDTO -> Response.ok(responseDTO).build())
                    .orElse(Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                            "book doesn't exist").build());
        } catch (BookNotFoundException | GenreNotFoundException | AuthorNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                    e.getMessage()).build(); // todo status comes without a message
        }
    }

    @DELETE
    @Path("delete/{bookId}")
    @Transactional
    @RolesAllowed("admin")
    public Response deleteBook(@PathParam("bookId") long id) {
        return bookService.delete(id) ?
                Response.ok().build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }
}
