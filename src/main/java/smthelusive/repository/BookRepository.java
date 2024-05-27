package smthelusive.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import smthelusive.entity.business.Book;
import smthelusive.resource.BookFilterParams;
import smthelusive.resource.PageParams;

import java.util.List;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {
    private static final String NO_FILTERING = "all";

    /***
     * Selects the books filtering by title (fuzzy), author (fuzzy), genre.
     * Returns back specified page of a specified page size.
     * @param bookFilterParams book filtering params
     * @param pageParams paging params
     * @return list of selected books
     */
    public List<Book> getBooksFilteredAndPaged(BookFilterParams bookFilterParams, PageParams pageParams) {
        Parameters parameters = new Parameters();
        String authorFiltering = "", titleFiltering = "", genreFiltering = "";
        if (filtered(bookFilterParams.getTitle())) {
            parameters = parameters.and("title", bookFilterParams.getTitle().toLowerCase() + "%");
            authorFiltering = "and (lower(concat(author.firstName, ' ', author.lastName)) like :author " +
                    "or lower(concat(author.lastName, ' ', author.firstName)) like :author) ";
        }
        if (filtered(bookFilterParams.getAuthor())) {
            parameters = parameters.and("author", String.join(" ",
                    bookFilterParams.getAuthor().split("\\s*-\\s*")).trim().toLowerCase() + "%");
            titleFiltering = "and lower(title) like :title ";
        }
        if (filtered(bookFilterParams.getGenre())) {
            parameters = parameters.and("genre", bookFilterParams.getGenre().toLowerCase());
            genreFiltering = "and lower(genre.name) = :genre ";
        }
        String query = String.format(
                "select book from Book book join book.genres genre join book.authors author where 1 = 1 %s%s%s",
                titleFiltering, genreFiltering, authorFiltering);
        return find(query, parameters).page(Page.of(pageParams.getPage(), pageParams.getPageSize())).list();
    }

    private boolean filtered(String value) {
        return !NO_FILTERING.equals(value);
    }

    public boolean delete(long id) {
        return delete("bookId", id) > 0;
    }
}
