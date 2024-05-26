package smthelusive.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import smthelusive.dto.request.AuthorRequestDTO;
import smthelusive.dto.request.BookRequestDTO;
import smthelusive.dto.request.GenreRequestDTO;
import smthelusive.dto.response.AuthorResponseDTO;
import smthelusive.dto.response.BookResponseDTO;
import smthelusive.dto.response.GenreResponseDTO;
import smthelusive.entity.business.Author;
import smthelusive.entity.business.Book;
import smthelusive.entity.business.Genre;

@Mapper(componentModel = "CDI")
public interface EntityMapper {
    BookResponseDTO toDTO(Book book);
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "bookId", ignore = true)
    Book toEntity(BookRequestDTO bookRequestDTO);
    @Mapping(target = "authorId", ignore = true)
    Author toEntity(AuthorRequestDTO authorRequestDTO);
    @Mapping(target = "genreId", ignore = true)
    Genre toEntity(GenreRequestDTO genreRequestDTO);
    AuthorResponseDTO toDTO(Author author);
    GenreResponseDTO toDTO(Genre genre);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "bookId", ignore = true)
    void updateBookFromDTO(BookRequestDTO bookRequestDTO, @MappingTarget Book book);

    @Mapping(target = "authorId", ignore = true)
    void updateAuthorFromDTO(AuthorRequestDTO authorRequestDTO, @MappingTarget Author book);

    @Mapping(target = "genreId", ignore = true)
    void updateGenreFromDTO(GenreRequestDTO genreRequestDTO, @MappingTarget Genre genre);
}
