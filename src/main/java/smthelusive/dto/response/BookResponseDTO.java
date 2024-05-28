package smthelusive.dto.response;

import java.util.HashSet;
import java.util.Set;

public class BookResponseDTO {
    private long bookId;
    private String title;
    private double price;
    private String annotation;
    private int count;
    private Set<AuthorResponseDTO> authors = new HashSet<>();
    private Set<GenreResponseDTO> genres = new HashSet<>();

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<AuthorResponseDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorResponseDTO> authors) {
        this.authors = authors;
    }

    public Set<GenreResponseDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreResponseDTO> genres) {
        this.genres = genres;
    }
}
