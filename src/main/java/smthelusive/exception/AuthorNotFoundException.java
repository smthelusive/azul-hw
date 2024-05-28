package smthelusive.exception;

import jakarta.ws.rs.NotFoundException;

public class AuthorNotFoundException extends NotFoundException {
    public AuthorNotFoundException(long authorId) {
        super(String.format("Author with id %s does not exist", authorId));
    }
}
