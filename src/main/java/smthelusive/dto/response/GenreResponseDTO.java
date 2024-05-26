package smthelusive.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class GenreResponseDTO {
    @NotEmpty
    private long genreId;
    @NotEmpty
    @Max(255)
    public String name;

    public GenreResponseDTO() {
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
