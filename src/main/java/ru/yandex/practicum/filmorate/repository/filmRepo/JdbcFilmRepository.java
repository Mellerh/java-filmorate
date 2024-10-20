package ru.yandex.practicum.filmorate.repository.filmRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.filmLikesRepository.FilmLikesRepository;
import ru.yandex.practicum.filmorate.service.genreService.GenreService;
import ru.yandex.practicum.filmorate.service.mpaService.MpaService;

import java.util.*;
import java.util.stream.Collectors;

// используем аннотацию @Qualifier, чтобы указывать её в @FilmServiceIml для указания Spring, какую реалзиацию инжектить
@Component
@Qualifier("jdbcFilmRepository")
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcOperations jdbc;


    private final MpaService mpaService;
    private final GenreService genreService;
    private final FilmLikesRepository likesRepository;


    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT * FROM films";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Film(
                rs.getLong("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("releaseDate").toLocalDate(),
                rs.getInt("duration"),
                new HashSet<>(likesRepository.getLikes(rs.getLong("film_id"))),
                mpaService.getMpaById(rs.getInt("mpa_id")),
                genreService.getFilmGenres(rs.getLong("film_id")))
        );
    }

    @Override
    public Film getFilmById(Long filmId) {

        String sql = "SELECT * FROM films WHERE film_id = ?";

        Film film = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Film(
                rs.getLong("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("releaseDate").toLocalDate(),
                rs.getInt("duration"),
                new HashSet<>(likesRepository.getLikes(rs.getLong("film_id"))),
                mpaService.getMpaById(rs.getInt("mpa_id")),
                genreService.getFilmGenres(rs.getLong("film_id"))), filmId);

        if (film != null) {
            if (film.getGenres().isEmpty()) {
                film.setGenres(null);
            }
        }
        return film;
    }

    @Override
    public Film saveFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        film.setMpa(mpaService.getMpaByIdWithCreation(film.getMpa().getId()));
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue());

        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genre.setName(genreService.getGenreByIdWithCreation(genre.getId()).getName());
            }
            genreService.putGenres(film);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE films SET " +
                "name = ?, description = ?, releaseDate = ?, duration = ?, " +
                "mpa_id = ? WHERE film_id = ?";
        if (jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()) != 0) {
            film.setMpa(mpaService.getMpaById(film.getMpa().getId()));
            if (film.getGenres() != null) {
                Collection<Genre> sortGenres = film.getGenres().stream()
                        .sorted(Comparator.comparing(Genre::getId))
                        .collect(Collectors.toList());
                film.setGenres(new LinkedHashSet<>(sortGenres));
                for (Genre genre : film.getGenres()) {
                    genre.setName(genreService.getGenreById(genre.getId()).getName());
                }
            }
            genreService.putGenres(film);
            return film;
        } else {
            throw new NotFoundException("Фильм с ID=" + film.getId() + " не найден!");
        }
    }

    @Override
    public void addFilmLikeByUser(Long filmId, Long userId) {
        String sql = "INSERT INTO film_user_likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteFilmLikeByUser(Long filmId, Long userId) {
        String sql = "DELETE FROM film_user_likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public Collection<Film> returnTopFilms(Long count) {
        String getPopularQuery = "SELECT film_id, name, description, release_date, duration, mpa_id " +
                "FROM films LEFT JOIN film_user_likes ON films.film_id = film_user_likes.film_id " +
                "GROUP BY films.film_id ORDER BY COUNT(film_likes.user_id) DESC LIMIT ?";

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

