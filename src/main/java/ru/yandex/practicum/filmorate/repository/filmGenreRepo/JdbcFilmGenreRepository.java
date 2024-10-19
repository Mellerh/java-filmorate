package ru.yandex.practicum.filmorate.repository.filmGenreRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.genreRepo.GenreRowMapper;

import java.util.LinkedHashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JdbcFilmGenreRepository implements FilmGenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final String ADD_FILM_GENRE =
            "INSERT INTO genre_film (genre_id, film_id) " +
                    "VALUES (:genreId, :filmId)";

    private final String DELETE_FILM_GENRE =
            "DELETE FROM genre_film WHERE genre_id = :genreId AND filmId = :filmId";

    private final String GET_ALL_FILM_GENRES =
            "SELECT genres.name " +
                    "FROM genre_film " +
                    "INNER JOIN genres ON genre_film.genre_id = genres.genre_id " +
                    "WHERE genre_film.film_id = :filmId";

    @Override
    public LinkedHashSet<Genre> getAllFilmGenresById(Long filmId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("filmId", filmId);

        try {
            List<Genre> genreList = jdbc.query(GET_ALL_FILM_GENRES, sqlParameterSource, new GenreRowMapper());
            // возвращаем LinkedHashSet, потому что требуется уникальность жанров у фильма
            return new LinkedHashSet<>(genreList);

        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }

    }

    @Override
    public void addFilmGenre(Long genreId, Long filmId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("genreId", genreId);
        sqlParameterSource.addValue("filmId", filmId);

        try {
            jdbc.update(ADD_FILM_GENRE, sqlParameterSource);
        } catch (EmptyResultDataAccessException ignored) {
        }

    }

    @Override
    public void deleteFilmGenre(Long genreId, Long filmId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("genreId", genreId);
        sqlParameterSource.addValue("filmId", filmId);

        try {
            jdbc.update(DELETE_FILM_GENRE, sqlParameterSource);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }

}
