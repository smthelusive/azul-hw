package smthelusive.service;

import org.mapstruct.Mapper;
import smthelusive.dto.AuthorDTO;
import smthelusive.dto.BookDTO;
import smthelusive.model.Author;
import smthelusive.model.Book;
import smthelusive.model.Genre;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "CDI")
public interface BookMapper {
    BookDTO toDTO(Book book);
    Author toEntity(AuthorDTO authorDTO);
    AuthorDTO toDTO(Author author);
    Set<Author> toDTOList(Set<AuthorDTO> authors);

    default Set<String> toGenreStrings(Set<Genre> genres) {
        return genres.stream().map(genre -> genre.name).collect(Collectors.toSet());
    }
    default Set<Genre> map(Set<String> values) {
        return values.stream().map(Genre::new).collect(Collectors.toSet());
    }
}
