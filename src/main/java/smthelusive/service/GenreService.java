package smthelusive.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smthelusive.dto.request.GenreRequestDTO;
import smthelusive.dto.response.GenreResponseDTO;
import smthelusive.exceptions.GenreNotFoundException;
import smthelusive.entity.business.Genre;
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

    public Optional<GenreResponseDTO> getSingleGenre(long id) {
        return genreRepository.find("genreId", id).firstResultOptional().map(entityMapper::toDTO);
    }

    public Optional<GenreResponseDTO> create(GenreRequestDTO genreRequestDTO) {
        Genre genre = entityMapper.toEntity(genreRequestDTO);
        genreRepository.persist(genre);
        return getSingleGenre(genre.getGenreId()); // todo think about this
    }

    public Optional<GenreResponseDTO> update(long genreId, GenreRequestDTO genreRequestDTO) throws GenreNotFoundException {
        Genre genre = genreRepository.find("genreId", genreId)
                .singleResultOptional().stream().findAny()
                .orElseThrow(() -> new GenreNotFoundException(
                        String.format("Genre with id %s does not exist", genreId)));
        entityMapper.updateGenreFromDTO(genreRequestDTO, genre);
        genreRepository.persist(genre);
        return getSingleGenre(genre.getGenreId()); // todo think about this
    }

    public boolean delete(long genreId) {
        return genreRepository.delete(genreId);
    }
}
