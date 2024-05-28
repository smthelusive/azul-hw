package smthelusive.exception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import smthelusive.exception.InvalidReferenceException;

@Provider
public class InvalidReferenceExceptionMapper implements ExceptionMapper<InvalidReferenceException> {
    @Override
    public Response toResponse(InvalidReferenceException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
