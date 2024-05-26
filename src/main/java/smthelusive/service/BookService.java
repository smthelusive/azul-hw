package smthelusive.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smthelusive.dto.BookDTO;
import smthelusive.repository.BookRepository;
import smthelusive.resource.BookFilterParams;
import smthelusive.resource.PageParams;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookService {
    @Inject
    private BookRepository bookRepository;
    @Inject
    private BookMapper bookMapper;
    public List<BookDTO> getBooksFiltered(BookFilterParams bookFilterParams, PageParams pageParams) {
        return bookRepository.getBooksFilteredAndPaged(bookFilterParams, pageParams).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookDTO> getSingleBook(long id) {
        return bookRepository.find("bookId", id).firstResultOptional().map(bookMapper::toDTO);
    }
}
