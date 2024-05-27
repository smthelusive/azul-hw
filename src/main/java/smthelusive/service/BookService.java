package smthelusive.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smthelusive.dto.request.BookRequestDTO;
import smthelusive.dto.response.BookResponseDTO;
import smthelusive.entity.business.Author;
import smthelusive.entity.business.Book;
import smthelusive.entity.business.Genre;
import smthelusive.exceptions.BookNotFoundException;
import smthelusive.exceptions.InvalidReferenceException;
import smthelusive.repository.AuthorRepository;
import smthelusive.repository.BookRepository;
import smthelusive.repository.GenreRepository;
import smthelusive.resource.BookFilterParams;
import smthelusive.resource.PageParams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public BookResponseDTO getSingleBook(long id) throws BookNotFoundException {
        return bookRepository.find("bookId", id).firstResultOptional()
                .map(entityMapper::toDTO).orElseThrow(() -> new BookNotFoundException(id));
    }

    private Set<Author> getResolvedAuthors(BookRequestDTO bookRequestDTO) throws InvalidReferenceException {
        Set<Author> newAuthors = new HashSet<>();
        for (Long authorId : bookRequestDTO.getAuthors()) {
            newAuthors.add(authorRepository
                    .find("authorId", authorId).singleResultOptional()
                    .stream().findAny()
                    .orElseThrow(() -> new InvalidReferenceException(
                            String.format("Author with id %s does not exist", authorId))));
        }
        return newAuthors;
    }
    private Set<Genre> getResolvedGenres(BookRequestDTO bookRequestDTO) throws InvalidReferenceException {
        Set<Genre> newGenres = new HashSet<>();
        for (Long genreId : bookRequestDTO.getGenres()) {
            newGenres.add(genreRepository
                    .find("genreId", genreId).singleResultOptional()
                    .stream().findAny()
                    .orElseThrow(() -> new InvalidReferenceException(
                            String.format("Genre with id %s does not exist", genreId))));
        }
        return newGenres;
    }

    public BookResponseDTO create(BookRequestDTO bookRequestDTO) throws InvalidReferenceException {
        Book book = entityMapper.toEntity(bookRequestDTO);
        Set<Author> resolvedAuthors = getResolvedAuthors(bookRequestDTO);
        Set<Genre> resolvedGenres = getResolvedGenres(bookRequestDTO); // todo
        book.genres = resolvedGenres;
        book.authors = resolvedAuthors;
        bookRepository.persist(book);
        return entityMapper.toDTO(book);
    }

    public BookResponseDTO update(long bookId, BookRequestDTO bookRequestDTO)
            throws BookNotFoundException, InvalidReferenceException {
        Book book = bookRepository.find("bookId", bookId).singleResultOptional().stream().findAny()
                .orElseThrow(() -> new BookNotFoundException(bookId));
        Set<Author> resolvedAuthors = getResolvedAuthors(bookRequestDTO);
        Set<Genre> resolvedGenres = getResolvedGenres(bookRequestDTO); // todo
        book.genres = resolvedGenres;
        book.authors = resolvedAuthors;
        entityMapper.updateBookFromDTO(bookRequestDTO, book);
        return entityMapper.toDTO(book);
    }

    public void delete(long bookId) {
        if (!bookRepository.delete(bookId)) {
            throw new BookNotFoundException(bookId);
        }
    }
}
