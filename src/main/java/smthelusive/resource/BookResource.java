package smthelusive.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    public Response getBooks(@BeanParam BookFilterParams bookFilterParams, @BeanParam PageParams pageParams) {
        return Response.ok(bookService.getBooksFiltered(bookFilterParams, pageParams)).build();
    }

    @GET
    @Path("{bookId}")
    public Response getSingleBook(@PathParam("bookId") long id) {
        return bookService.getSingleBook(id).map(book -> Response.ok(book).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
