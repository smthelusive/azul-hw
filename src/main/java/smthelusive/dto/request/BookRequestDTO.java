package smthelusive.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.testcontainers.shaded.org.checkerframework.checker.optional.qual.Present;

import java.util.HashSet;
import java.util.Set;

public class BookRequestDTO {
    @NotEmpty
    @Size(max = 255)
    private String title;
    @Present
    private double price;
    @Size(max = 1000)
    private String annotation;
    private int count;

    @NotEmpty
    private Set<Long> authors = new HashSet<>();
    private Set<Long> genres = new HashSet<>();

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

    public Set<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Long> authors) {
        this.authors = authors;
    }

    public Set<Long> getGenres() {
        return genres;
    }

    public void setGenres(Set<Long> genres) {
        this.genres = genres;
    }
}
