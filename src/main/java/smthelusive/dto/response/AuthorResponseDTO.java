package smthelusive.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class AuthorResponseDTO {
    @NotEmpty
    private long authorId;
    @NotEmpty
    @Max(255)
    private String firstName;
    @NotEmpty
    @Max(255)
    private String lastName;
    @Max(1000)
    private String bio;

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
