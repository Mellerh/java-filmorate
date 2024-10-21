package ru.yandex.practicum.filmorate.repository.genreRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JbdcGenreRepository implements GenreRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Genre> getGenres() {
        String sql = "SELECT * FROM genres ORDER BY genre_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Genre(
                rs.getInt("genre_id"),
                rs.getString("name"))
        );
    }

    @Override
    public Genre getGenreById(Integer genreId) {

        try {
            return jdbcTemplate.queryForObject("SELECT genre_id, name FROM genres WHERE genre_id = ?", (rs, rowNum)
                    -> new Genre(rs.getInt("genre_id"), rs.getString("name")), genreId);
        } catch (Exception e) {
            log.warn("Проблема запроса getGenreById c " + genreId);
            return null;
        }
    }

    @Override
    public List<Genre> getAllGenresByIds(Set<Integer> genreIds) {
        String sql = "SELECT * FROM genres WHERE genre_id IN (:ids)";
        return namedParameterJdbcTemplate.query(sql, Map.of("ids", genreIds), (rs, rowNum) -> new Genre(
                rs.getInt("genre_id"),
                rs.getString("name"))
        );
    }

    @Override
    public void delete(Film film) {
        jdbcTemplate.update("DELETE FROM genre_film WHERE film_id = ?", film.getId());
    }

    @Override
    public void add(Film film) {
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update("INSERT INTO genre_film (film_id, genre_id) VALUES (?, ?)",
                        film.getId(), genre.getId());
            }
        }
    }

    @Override
    public List<Genre> getFilmGenres(Long filmId) {
        String sql = "SELECT genres.genre_id AS gnrId, genres.name AS gnrName " +
                "FROM genre_film " +
                "INNER JOIN genres ON genre_film.genre_id = genres.genre_id WHERE genre_film.film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Genre(
                rs.getInt("gnrId"), rs.getString("gnrName")), filmId
        );
    }
}
