package ru.yandex.practicum.filmorate.repository.filmLikesRepository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.genreService.GenreService;
import ru.yandex.practicum.filmorate.service.mpaService.MpaService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcFilmLikesRepository implements FilmLikesRepository {

    private final JdbcTemplate jdbcTemplate;
    private MpaService mpaService;
    private GenreService genreService;

    @Override
    public void addLike(Long filmId, Long userId) {
        String sql = "INSERT INTO film_user_likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        String sql = "DELETE FROM film_user_likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getPopular(Integer count) {
        String getPopularQuery = "SELECT films.film_id AS fmId, films.name AS fmName, films.description AS fmDesc, " +
                "films.releaseDate AS fmDate, films.duration AS fmDur, films.mpa_id AS fmMpaId " +
                "FROM films LEFT JOIN film_user_likes ON films.film_id = film_user_likes.film_id " +
                "GROUP BY films.film_id ORDER BY COUNT(film_user_likes.user_id) DESC LIMIT ?";

        return jdbcTemplate.query(getPopularQuery, (rs, rowNum) -> new Film(
                        rs.getLong("fmId"),
                        rs.getString("fmName"),
                        rs.getString("fmDesc"),
                        rs.getDate("fmDate").toLocalDate(),
                        rs.getInt("fmDur"),
                        new HashSet<>(getLikes(rs.getLong("fmId"))),
                        mpaService.getMpaById(rs.getInt("fmMpaId")),
                        new LinkedHashSet<>(genreService.getFilmGenres(rs.getLong("fmId")))),
                count);
    }

    @Override
    public List<Long> getLikes(Long filmId) {
        String sql = "SELECT user_id FROM film_user_likes WHERE film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("user_id"), filmId);
    }

}
