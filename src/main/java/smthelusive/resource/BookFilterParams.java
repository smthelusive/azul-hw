package smthelusive.resource;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class BookFilterParams {
    @QueryParam("title")
    @DefaultValue("all")
    private String title;
    @QueryParam("author")
    @DefaultValue("all")
    private String author;

    @QueryParam("genre")
    @DefaultValue("all")
    private String genre;

    public BookFilterParams() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
