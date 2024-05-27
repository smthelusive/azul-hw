package smthelusive.exceptions;

import jakarta.ws.rs.NotFoundException;

public class GenreNotFoundException extends NotFoundException {
    public GenreNotFoundException(long genreId) {
        super(String.format("Genre with id %s does not exist", genreId));

    }
}
