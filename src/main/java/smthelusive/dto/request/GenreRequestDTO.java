package smthelusive.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class GenreRequestDTO {
    @NotEmpty
    @Max(255)
    public String name;

    public GenreRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
