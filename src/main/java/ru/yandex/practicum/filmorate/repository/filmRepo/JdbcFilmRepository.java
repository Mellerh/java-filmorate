package ru.yandex.practicum.filmorate.repository.filmRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.filmLikesRepository.FilmLikesRepository;
import ru.yandex.practicum.filmorate.service.genreService.GenreService;
import ru.yandex.practicum.filmorate.service.mpaService.MpaService;

import java.util.*;

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
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_Date").toLocalDate(),
                rs.getInt("duration"),
                new HashSet<>(likesRepository.getLikes(rs.getLong("film_id"))),
                mpaService.getMpaById(rs.getInt("mpa_id")),
                genreService.getFilmGenres(rs.getLong("film_id")))
        );
    }

    @Override
    public Film getFilmById(Long filmId) {

        Film film;
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE film_id = ?", filmId);
        if (filmRows.first()) {
            Mpa mpa = mpaService.getMpaById(filmRows.getInt("mpa_id"));
            Set<Genre> genres = genreService.getFilmGenres(filmId);
            film = new Film(
                    filmRows.getLong("id"),
                    filmRows.getString("name"),
                    filmRows.getString("description"),
                    filmRows.getDate("releaseDate").toLocalDate(),
                    filmRows.getInt("duration"),
                    new HashSet<>(likesRepository.getLikes(filmRows.getLong("id"))),
                    mpa,
                    genres);
        } else {
            throw new NotFoundException("Фильм с ID=" + filmId + " не найден!");
        }
        if (film.getGenres().isEmpty()) {
            film.setGenres(null);
        }
        return film;
    }

    @Override
    public Film saveFilm(Film film) {
        // GeneratedKeyHolder для получения сгенерированного ключа из таблицы
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        // MapSqlParameterSource для параметризированных SQL-запросов
        MapSqlParameterSource sqlParameterSource = updateParams(film);

        // вставляем данные в таблицу films
        jdbc.update("INSERT INTO films (name, description, releaseDate, duration) " +
                        "VALUES(:name, :description, :releaseDate, :duration)",
                sqlParameterSource,
                keyHolder,
                new String[]{"film_id"});


        Number key = keyHolder.getKey();
        if (key != null) {
            film.setId(key.longValue());
        }

        film.setMpa(mpaService.getMpaById(film.getMpa().getId()));
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genre.setName(genreService.getGenreById(genre.getId()).getName());
            }
            genreService.putGenres(film);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film updatedFilm) {

        String sqlQuery = "UPDATE films SET " +
                "name = ?, description = ?, release_date = ?, duration = ?, " +
                "rating_id = ? WHERE id = ?";
        if (jdbcTemplate.update(sqlQuery,
                updatedFilm.getName(),
                updatedFilm.getDescription(),
                updatedFilm.getReleaseDate(),
                updatedFilm.getDuration(),
                updatedFilm.getMpa().getId(),
                updatedFilm.getId()) != 0) {
            updatedFilm.setMpa(mpaService.getMpaById(updatedFilm.getMpa().getId()));

            if (updatedFilm.getGenres() != null) {
                Collection<Genre> sortGenres = updatedFilm.getGenres().stream()
                        .sorted(Comparator.comparing(Genre::getId))
                        .toList();
                updatedFilm.setGenres(new LinkedHashSet<>(sortGenres));
                for (Genre genre : updatedFilm.getGenres()) {
                    genre.setName(genreService.getGenreById(genre.getId()).getName());
                }
            }

            genreService.putGenres(updatedFilm);
            return updatedFilm;
        } else {
            throw new NotFoundException("Фильм с ID=" + updatedFilm.getId() + " не найден!");
        }
    }

    @Override
    public void addFilmLikeByUser(Long filmId, Long userId) {

    }

    @Override
    public void deleteFilmLikeByUser(Long filmId, Long userId) {

    }

    @Override
    public Collection<Film> returnTopFilms(Long count) {
        return List.of();
    }


    private MapSqlParameterSource updateParams(Film film) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name", film.getName());
        sqlParameterSource.addValue("description", film.getDescription());
        sqlParameterSource.addValue("releaseDate", film.getReleaseDate());
        sqlParameterSource.addValue("duration", film.getDuration());
        return sqlParameterSource;
    }
}

