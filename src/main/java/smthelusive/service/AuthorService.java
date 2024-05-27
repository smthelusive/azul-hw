package smthelusive.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smthelusive.dto.request.AuthorRequestDTO;
import smthelusive.dto.response.AuthorResponseDTO;
import smthelusive.exceptions.AuthorNotFoundException;
import smthelusive.entity.business.Author;
import smthelusive.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthorService {
    @Inject
    private AuthorRepository authorRepository;
    @Inject
    private EntityMapper entityMapper;
    public List<AuthorResponseDTO> getAll() {
        return authorRepository.findAll().list().stream()
                .map(author -> entityMapper.toDTO(author)).collect(Collectors.toList());
    }

    public AuthorResponseDTO getSingleAuthor(long id) throws AuthorNotFoundException {
        return authorRepository.find("authorId", id).firstResultOptional()
                .map(entityMapper::toDTO).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public AuthorResponseDTO create(AuthorRequestDTO authorRequestDTO) {
        Author author = entityMapper.toEntity(authorRequestDTO); // todo can fail here if request is malformed
        authorRepository.persist(author);
        return entityMapper.toDTO(author);
    }

    public AuthorResponseDTO update(long authorId, AuthorRequestDTO authorRequestDTO) throws AuthorNotFoundException {
        Author author = authorRepository.find("authorId", authorId)
                .singleResultOptional().stream().findAny()
                .orElseThrow(() -> new AuthorNotFoundException(authorId));
        entityMapper.updateAuthorFromDTO(authorRequestDTO, author);
        return entityMapper.toDTO(author);
    }

    public void delete(long authorId) throws AuthorNotFoundException {
        if (!authorRepository.delete(authorId)) {
            throw new AuthorNotFoundException(authorId);
        }
    }
}
