package smthelusive.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class GenreRequestDTO {
    @NotEmpty
    @Size(max = 255)
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
