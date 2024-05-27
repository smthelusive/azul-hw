package smthelusive.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smthelusive.dto.request.GenreRequestDTO;
import smthelusive.dto.response.GenreResponseDTO;
import smthelusive.entity.business.Genre;
import smthelusive.exceptions.GenreNotFoundException;
import smthelusive.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GenreService {
    @Inject
    private GenreRepository genreRepository;
    @Inject
    private EntityMapper entityMapper;

    /***
     * Get all genres
     * @return List of GenreResponseDTO containing data of all genres
     */
    public List<GenreResponseDTO> getAll() {
        return genreRepository.findAll().list().stream()
                .map(genre -> entityMapper.toDTO(genre)).collect(Collectors.toList());
    }

    /***
     * Get single genre by id
     * @param id long genre id
     * @return GenreResponseDTO containing data of a genre
     * @throws GenreNotFoundException if genre with requested id doesn't exist
     */
    public GenreResponseDTO getSingleGenre(long id) throws GenreNotFoundException {
        return genreRepository.find("genreId", id).firstResultOptional().map(entityMapper::toDTO)
                .orElseThrow(() -> {
                    Log.error(String.format("Genre with id %s doesn't exist", id));
                    return new GenreNotFoundException(id);
                });
    }

    /***
     * Create new genre
     * @param genreRequestDTO containing genre data
     * @return GenreResponseDTO containing data of a created genre
     */
    public GenreResponseDTO create(GenreRequestDTO genreRequestDTO) {
        Genre genre = entityMapper.toEntity(genreRequestDTO);
        genreRepository.persist(genre);
        Log.info(String.format("Genre with id %s successfully created", genre.getGenreId()));
        return entityMapper.toDTO(genre);
    }

    /***
     * Update genre by id
     * @param genreId long genre id
     * @param genreRequestDTO containing new genre data
     * @return GenreResponseDTO containing data of updated genre
     * @throws GenreNotFoundException if genre doesn't exist
     */
    public GenreResponseDTO update(long genreId, GenreRequestDTO genreRequestDTO) throws GenreNotFoundException {
        Genre genre = genreRepository.find("genreId", genreId)
                .singleResultOptional().stream().findAny()
                .orElseThrow(() -> {
                    Log.error(String.format("Genre with id %s doesn't exist", genreId));
                    return new GenreNotFoundException(genreId);
                });
        entityMapper.updateGenreFromDTO(genreRequestDTO, genre);
        Log.info(String.format("Genre with id %s successfully updated", genreId));
        return entityMapper.toDTO(genre);
    }

    /***
     * Delete genre by id if exists
     * @param genreId long genre id
     * @throws GenreNotFoundException if genre doesn't exist
     */
    public void delete(long genreId) throws GenreNotFoundException {
        if (!genreRepository.delete(genreId)) {
            Log.error(String.format("Genre with id %s doesn't exist", genreId));
            throw new GenreNotFoundException(genreId);
        }
        Log.info(String.format("Genre with id %s successfully deleted", genreId));
    }
}
