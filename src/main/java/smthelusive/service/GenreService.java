package smthelusive.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import smthelusive.dto.request.GenreRequestDTO;
import smthelusive.dto.response.GenreResponseDTO;
import smthelusive.entity.business.Genre;
import smthelusive.exceptions.BookNotFoundException;
import smthelusive.exceptions.GenreNotFoundException;
import smthelusive.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class GenreService {
    @Inject
    private GenreRepository genreRepository;
    @Inject
    private EntityMapper entityMapper;
    public List<GenreResponseDTO> getAll() {
        return genreRepository.findAll().list().stream()
                .map(genre -> entityMapper.toDTO(genre)).collect(Collectors.toList());
    }

    public GenreResponseDTO getSingleGenre(long id) throws GenreNotFoundException {
        return genreRepository.find("genreId", id).firstResultOptional().map(entityMapper::toDTO)
                .orElseThrow(() -> new GenreNotFoundException(id));
    }

    public GenreResponseDTO create(GenreRequestDTO genreRequestDTO) {
        Genre genre = entityMapper.toEntity(genreRequestDTO);
        genreRepository.persist(genre);
        return entityMapper.toDTO(genre);
    }

    public GenreResponseDTO update(long genreId, GenreRequestDTO genreRequestDTO) throws GenreNotFoundException {
        Genre genre = genreRepository.find("genreId", genreId)
                .singleResultOptional().stream().findAny()
                .orElseThrow(() -> new GenreNotFoundException(genreId));
        entityMapper.updateGenreFromDTO(genreRequestDTO, genre);
        return entityMapper.toDTO(genre);
    }

    public void delete(long genreId) {
        if (!genreRepository.delete(genreId)) {
            throw new GenreNotFoundException(genreId);
        }
    }
}
