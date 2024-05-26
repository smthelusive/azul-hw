package smthelusive.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

public class BookDTO {
    @NotEmpty
    private long bookId;
    @NotEmpty
    @Max(255)
    public String title;
    @NotEmpty
    public double price;
    @Max(1000)
    public String annotation;
    public int count;
    @NotEmpty
    private Set<@Valid AuthorDTO> authors = new HashSet<>();
    private Set<String> genres = new HashSet<>(); // todo genre names should be unique in the DB

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

    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }
}
