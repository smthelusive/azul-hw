package smthelusive.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import smthelusive.entity.business.Author;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author> {
    public boolean delete(long id) {
        return delete("authorId", id) > 0;
    }
}
