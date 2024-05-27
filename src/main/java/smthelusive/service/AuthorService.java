package smthelusive.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smthelusive.dto.request.AuthorRequestDTO;
import smthelusive.dto.response.AuthorResponseDTO;
import smthelusive.exceptions.AuthorNotFoundException;
import smthelusive.entity.business.Author;
import smthelusive.repository.AuthorRepository;
import io.quarkus.logging.Log;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthorService {
    @Inject
    private AuthorRepository authorRepository;
    @Inject
    private EntityMapper entityMapper;

    /***
     * Get all authors
     * @return List of AuthorResponseDTO containing data about authors
     */
    public List<AuthorResponseDTO> getAll() {
        return authorRepository.findAll().list().stream()
                .map(author -> entityMapper.toDTO(author)).collect(Collectors.toList());
    }

    /***
     * Get author by id
     * @param id long author id
     * @return AuthorResponseDTO containing data of an author
     * @throws AuthorNotFoundException if author with such id doesn't exist
     */
    public AuthorResponseDTO getSingleAuthor(long id) throws AuthorNotFoundException {
        return authorRepository.find("authorId", id).firstResultOptional()
                .map(entityMapper::toDTO).orElseThrow(() -> {
                    Log.error(String.format("Author with id %s doesn't exist", id));
                    return new AuthorNotFoundException(id);
                });
    }

    /***
     * Create new author
     * @param authorRequestDTO containing author data
     * @return AuthorResponseDTO containing data of a newly created author
     */
    public AuthorResponseDTO create(AuthorRequestDTO authorRequestDTO) {
        Author author = entityMapper.toEntity(authorRequestDTO);
        authorRepository.persist(author);
        Log.info(String.format("Author with id %s successfully created", author.getAuthorId()));
        return entityMapper.toDTO(author);
    }

    /***
     * Update author by id
     * @param authorId long author id
     * @param authorRequestDTO containing new author data
     * @return AuthorResponseDTO containing data of updated author
     * @throws AuthorNotFoundException if author does not exist
     */
    public AuthorResponseDTO update(long authorId, AuthorRequestDTO authorRequestDTO) throws AuthorNotFoundException {
        Author author = authorRepository.find("authorId", authorId)
                .singleResultOptional().stream().findAny()
                .orElseThrow(() -> {
                    Log.error(String.format("Author with id %s doesn't exist", authorId));
                    return new AuthorNotFoundException(authorId);
                });
        entityMapper.updateAuthorFromDTO(authorRequestDTO, author);
        Log.info(String.format("Author with id %s successfully updated", authorId));
        return entityMapper.toDTO(author);
    }

    /***
     * Delete author by id
     * @param authorId long author id
     * @throws AuthorNotFoundException if author doesn't exist
     */
    public void delete(long authorId) throws AuthorNotFoundException {
        if (!authorRepository.delete(authorId)) {
            Log.error(String.format("Author with id %s doesn't exist", authorId));
            throw new AuthorNotFoundException(authorId);
        }
        Log.info(String.format("Author with id %s successfully deleted", authorId));
    }
}
