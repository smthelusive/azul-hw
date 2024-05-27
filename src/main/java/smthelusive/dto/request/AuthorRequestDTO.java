package smthelusive.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthorRequestDTO {
    @NotEmpty
    @Size(max = 255)
    private String firstName;
    @NotEmpty
    @Size(max = 255)
    private String lastName;
    @Size(max = 1000)
    private String bio;

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
