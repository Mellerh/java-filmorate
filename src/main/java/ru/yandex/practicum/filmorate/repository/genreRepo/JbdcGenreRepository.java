package ru.yandex.practicum.filmorate.repository.genreRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JbdcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    private static final String GET_GENRE_BY_ID = "SELECT * FROM genres WHERE genre_id = :genre_id";
    private static final String GET_ALL_GENRES = "SELECT * FROM genres";


    @Override
    public Genre getGenreById(Integer id) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("genre_id", id);

        try {
            return jdbc.queryForObject(GET_GENRE_BY_ID, sqlParameterSource, new GenreRowMapper());

        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }
    }

    @Override
    public Collection<Genre> getAllGenres() {
        try {
            return jdbc.query(GET_ALL_GENRES, new GenreRowMapper());
        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }
    }
}
