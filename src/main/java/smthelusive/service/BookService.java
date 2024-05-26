package smthelusive.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smthelusive.dto.request.BookRequestDTO;
import smthelusive.dto.response.BookResponseDTO;
import smthelusive.exceptions.AuthorNotFoundException;
import smthelusive.exceptions.BookNotFoundException;
import smthelusive.exceptions.GenreNotFoundException;
import smthelusive.entity.business.Book;
import smthelusive.repository.AuthorRepository;
import smthelusive.repository.BookRepository;
import smthelusive.repository.GenreRepository;
import smthelusive.resource.BookFilterParams;
import smthelusive.resource.PageParams;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookService {
    @Inject
    private BookRepository bookRepository;
    @Inject
    private AuthorRepository authorRepository;
    @Inject
    private GenreRepository genreRepository;
    @Inject
    private EntityMapper entityMapper;
    public List<BookResponseDTO> getBooksFiltered(BookFilterParams bookFilterParams, PageParams pageParams) {
        return bookRepository.getBooksFilteredAndPaged(bookFilterParams, pageParams).stream()
                .map(entityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookResponseDTO> getSingleBook(long id) {
        return bookRepository.find("bookId", id).firstResultOptional().map(entityMapper::toDTO);
    }

    private void enrichWithRelatedEntities(BookRequestDTO bookRequestDTO, Book book) throws AuthorNotFoundException, GenreNotFoundException {
        book.authors = new HashSet<>();
        book.genres = new HashSet<>();
        for (Long authorId : bookRequestDTO.getAuthors()) {
            book.authors.add(authorRepository
                    .find("authorId", authorId).singleResultOptional()
                    .stream().findAny()
                    .orElseThrow(() -> new AuthorNotFoundException(
                            String.format("Author with id %s does not exist", authorId))));
        }

        for (Long genreId : bookRequestDTO.getGenres()) {
            book.genres.add(genreRepository
                    .find("genreId", genreId).singleResultOptional() // todo refactor
                    .stream().findAny()
                    .orElseThrow(() -> new GenreNotFoundException(
                            String.format("Genre with id %s does not exist", genreId))));
        }
    }

    public Optional<BookResponseDTO> create(BookRequestDTO bookRequestDTO)
            throws AuthorNotFoundException, GenreNotFoundException {
        Book book = entityMapper.toEntity(bookRequestDTO);
        enrichWithRelatedEntities(bookRequestDTO, book);
        bookRepository.persist(book);
        return getSingleBook(book.bookId); // todo think about this
    }

    public Optional<BookResponseDTO> update(long bookId, BookRequestDTO bookRequestDTO)
            throws BookNotFoundException, AuthorNotFoundException, GenreNotFoundException {
        Book book = bookRepository.find("bookId", bookId).singleResultOptional().stream().findAny()
                .orElseThrow(() -> new BookNotFoundException(String.format("Book with id %s does not exist", bookId)));
        entityMapper.updateBookFromDTO(bookRequestDTO, book);
        enrichWithRelatedEntities(bookRequestDTO, book);
        bookRepository.persist(book);
        return getSingleBook(book.bookId); // todo think about this
    }

    public boolean delete(long bookId) {
        return bookRepository.delete(bookId);
    }
}
