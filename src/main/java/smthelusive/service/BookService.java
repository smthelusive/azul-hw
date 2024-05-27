package smthelusive.service;

import io.quarkus.logging.Log;
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

    /***
     * Get all books optionally filtered by title, author, genre.
     * Filtering by author and title is fuzzy.
     * Performs paging, default page size is 10, default page is 0 (first page).
     * @param bookFilterParams encapsulates optional filter params: author, title, genre
     * @param pageParams encapsulates optional paging params: page, pageSize
     * @return List of BookResponseDTO containing book data of filtered books
     */
    public List<BookResponseDTO> getBooksFiltered(BookFilterParams bookFilterParams, PageParams pageParams) {
        return bookRepository.getBooksFilteredAndPaged(bookFilterParams, pageParams).stream()
                .map(entityMapper::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * Get single book by id
     * @param id long book id
     * @return BookResponseDTO containing book data
     * @throws BookNotFoundException if book doesn't exist
     */
    public BookResponseDTO getSingleBook(long id) throws BookNotFoundException {
        return bookRepository.find("bookId", id).firstResultOptional()
                .map(entityMapper::toDTO).orElseThrow(() -> {
                    Log.error(String.format("Book with id %s does not exist", id));
                    return new BookNotFoundException(id);
                });
    }

    private Set<Author> getResolvedAuthors(BookRequestDTO bookRequestDTO) throws InvalidReferenceException {
        Set<Author> newAuthors = new HashSet<>();
        for (Long authorId : bookRequestDTO.getAuthors()) {
            newAuthors.add(authorRepository
                    .find("authorId", authorId).singleResultOptional()
                    .stream().findAny()
                    .orElseThrow(() -> {
                        String message = String.format("Author with id %s does not exist", authorId);
                        Log.error(message);
                        return new InvalidReferenceException(message);
                    }));
        }
        Log.info("Book authors resolved");
        return newAuthors;
    }

    private Set<Genre> getResolvedGenres(BookRequestDTO bookRequestDTO) throws InvalidReferenceException {
        Set<Genre> newGenres = new HashSet<>();
        for (Long genreId : bookRequestDTO.getGenres()) {
            newGenres.add(genreRepository
                    .find("genreId", genreId).singleResultOptional()
                    .stream().findAny()
                    .orElseThrow(() -> {
                        String message = String.format("Genre with id %s does not exist", genreId);
                        Log.error(message);
                        return new InvalidReferenceException(message);
                    }));
        }
        Log.info("Book genres resolved");
        return newGenres;
    }

    /***
     * Create new book
     * @param bookRequestDTO containing book data
     * @return BookResponseDTO containing data of created book
     * @throws InvalidReferenceException if any of referenced genres or authors does not exist
     */
    public BookResponseDTO create(BookRequestDTO bookRequestDTO) throws InvalidReferenceException {
        Book book = entityMapper.toEntity(bookRequestDTO);
        updateBooksAuthorsAndGenres(book, getResolvedAuthors(bookRequestDTO), getResolvedGenres(bookRequestDTO));
        bookRepository.persist(book);
        Log.info(String.format("Book with id %s successfully created", book.getBookId()));
        return entityMapper.toDTO(book);
    }

    /***
     * Update book by id
     * @param bookId long book id
     * @param bookRequestDTO containing new book data
     * @return BookResponseDTO containing data of updated book
     * @throws BookNotFoundException if book doesn't exist
     * @throws InvalidReferenceException if any of referenced genres or authors does not exist
     */
    public BookResponseDTO update(long bookId, BookRequestDTO bookRequestDTO)
            throws BookNotFoundException, InvalidReferenceException {
        Book book = bookRepository.find("bookId", bookId).singleResultOptional().stream().findAny()
                .orElseThrow(() -> {
                    Log.error(String.format("Book with id %s doesn't exist", bookId));
                    return new BookNotFoundException(bookId);
                });
        updateBooksAuthorsAndGenres(book, getResolvedAuthors(bookRequestDTO), getResolvedGenres(bookRequestDTO));
        entityMapper.updateBookFromDTO(bookRequestDTO, book);
        Log.info(String.format("Book with id %s successfully updated", bookId));
        return entityMapper.toDTO(book);
    }

    private void updateBooksAuthorsAndGenres(Book book, Set<Author> authors, Set<Genre> genres) {
        book.genres = genres;
        book.authors = authors;
    }

    /***
     * Delete book by id
     * @param bookId long book id
     * @throws BookNotFoundException if book doesn't exist
     */
    public void delete(long bookId) throws BookNotFoundException {
        if (!bookRepository.delete(bookId)) {
            Log.error(String.format("Book with id %s doesn't exist", bookId));
            throw new BookNotFoundException(bookId);
        }
        Log.info(String.format("Book with id %s successfully deleted", bookId));
    }
}
