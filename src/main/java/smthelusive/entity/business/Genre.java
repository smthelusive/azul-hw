package smthelusive.entity.business;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "genre_id")
    private long genreId;
    private String name;
    @ManyToMany(mappedBy = "genres")
    public Set<Book> books = new HashSet<>();

    public Genre(String name) {
        this.name = name;
    }

    public Genre() {}

    public long getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
