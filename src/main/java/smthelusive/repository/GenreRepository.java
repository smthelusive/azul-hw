package smthelusive.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import smthelusive.model.Genre;

@ApplicationScoped
public class GenreRepository implements PanacheRepository<Genre> {
    public boolean delete(long id) {
        return delete("genreId", id) > 0;
    }
}
