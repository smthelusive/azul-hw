package smthelusive.exception;

import jakarta.ws.rs.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(long bookId) {
        super(String.format("Book with id %s does not exist", bookId));
    }
}
