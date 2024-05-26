package smthelusive.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import smthelusive.model.Book;
import smthelusive.resource.BookFilterParams;
import smthelusive.resource.PageParams;

import java.util.List;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {
    private static final String NO_FILTERING = "all";
    public List<Book> getBooksFilteredAndPaged(BookFilterParams bookFilterParams, PageParams pageParams) {
        Parameters parameters = new Parameters();
        if (!NO_FILTERING.equals(bookFilterParams.getTitle())) parameters = parameters.and("title", bookFilterParams.getTitle().toLowerCase() + "%");
        if (!NO_FILTERING.equals(bookFilterParams.getAuthor())) parameters = parameters.and("author", String.join(" ",
                bookFilterParams.getAuthor().split("\\s*-\\s*")).trim().toLowerCase() + "%");
        if (!NO_FILTERING.equals(bookFilterParams.getGenre())) parameters = parameters.and("genre", bookFilterParams.getGenre().toLowerCase());
//        parameters.map().values().forEach(System.out::println);
        String query = String.format("select book from Book book join book.genres genre join book.authors author where 1 = 1 %s%s%s",
                !NO_FILTERING.equals(bookFilterParams.getTitle()) ? "and lower(title) like :title " : "",
                !NO_FILTERING.equals(bookFilterParams.getGenre()) ? "and lower(genre.name) = :genre " : "",
                !NO_FILTERING.equals(bookFilterParams.getAuthor()) ? "and (lower(concat(author.firstName, ' ', author.lastName)) like :author " +
                        "or lower(concat(author.lastName, ' ', author.firstName)) like :author) " : "");
//        System.out.println(query);
        return find(query, parameters).page(Page.of(pageParams.getPages(), pageParams.getPageSize())).list();
    }

    public void delete(long id) {
        delete("bookId", id);
    }

    // todo use this for create/update and validate on controller level
    public void persist(Book book) {
        // todo update values to lowercase
        PanacheRepository.super.persist(book);
    }

//    public void updateInfo(long bookId, String title, double price, int count, String annotation) {
//        Book book = find("bookId", bookId).singleResult();
//        // todo lombock set and to lowercase
//        PanacheRepository.super.persist(book);
//    }
//
//    public void addAuthor(long bookId, Author author) {
//        Book book = find("bookId", bookId).singleResult();
//        book.authors.add(author);
//        PanacheRepository.super.persist(book);
//    }
//
//    public void removeAuthor(long bookId, Author author) {
//        Book book = find("bookId", bookId).singleResult();
//        book.authors.remove(author);
//        PanacheRepository.super.persist(book);
//    }
}
