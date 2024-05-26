package smthelusive.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smthelusive.dto.request.AuthorRequestDTO;
import smthelusive.dto.response.AuthorResponseDTO;
import smthelusive.exceptions.AuthorNotFoundException;
import smthelusive.entity.business.Author;
import smthelusive.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
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

    public Optional<AuthorResponseDTO> getSingleAuthor(long id) {
        return authorRepository.find("authorId", id).firstResultOptional().map(entityMapper::toDTO);
    }

    public Optional<AuthorResponseDTO> create(AuthorRequestDTO authorRequestDTO) {
        Author author = entityMapper.toEntity(authorRequestDTO);
        authorRepository.persist(author);
        return getSingleAuthor(author.authorId); // todo think about this
    }

    public Optional<AuthorResponseDTO> update(long authorId, AuthorRequestDTO authorRequestDTO) throws AuthorNotFoundException {
        Author author = authorRepository.find("authorId", authorId)
                .singleResultOptional().stream().findAny()
                .orElseThrow(() -> new AuthorNotFoundException(
                        String.format("Author with id %s does not exist", authorId)));
        entityMapper.updateAuthorFromDTO(authorRequestDTO, author);
        authorRepository.persist(author);
        return getSingleAuthor(author.authorId); // todo think about this
    }

    public boolean delete(long authorId) {
        return authorRepository.delete(authorId);
    }
}
