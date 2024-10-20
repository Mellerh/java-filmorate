package ru.yandex.practicum.filmorate.repository.filmLikesRepository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.genreService.GenreService;
import ru.yandex.practicum.filmorate.service.mpaService.MpaService;

import java.util.HashSet;
import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcFilmLikesRepository implements FilmLikesRepository {

    private final JdbcTemplate jdbcTemplate;
    private MpaService mpaService;
    private GenreService genreService;


    public void addLike(Long filmId, Long userId) {
        String sql = "INSERT INTO film_user_likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        String sql = "DELETE FROM film_user_likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    public List<Film> getPopular(Integer count) {
        String getPopularQuery = "SELECT film_id, name, description, releaseDate, duration, mpa_id " +
                "FROM films LEFT JOIN film_user_likes ON films.film_id = film_user_likes.film_id " +
                "GROUP BY films.film_id ORDER BY COUNT(film_user_likes.user_id) DESC LIMIT ?";

        return jdbcTemplate.query(getPopularQuery, (rs, rowNum) -> new Film(
                        rs.getLong("film_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("releaseDate").toLocalDate(),
                        rs.getInt("duration"),
                        new HashSet<>(getLikes(rs.getLong("film_id"))),
                        mpaService.getMpaById(rs.getInt("mpa_id")),
                        genreService.getFilmGenres(rs.getLong("film_id"))),
                count);
    }

    public List<Long> getLikes(Long filmId) {
        String sql = "SELECT user_id FROM film_user_likes WHERE film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("user_id"), filmId);
    }

}
