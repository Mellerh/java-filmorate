package ru.yandex.practicum.filmorate.repository.filmRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.filmGenreRepo.FilmGenreRepository;
import ru.yandex.practicum.filmorate.repository.mpaRepo.MpaRepository;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

// используем аннотацию @Qualifier, чтобы указывать её в @FilmServiceIml для указания Spring, какую реалзиацию инжектить
@Component
@Qualifier("jdbcFilmRepository")
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {

    private final String FIND_BY_ID =
            "SELECT films.*, " +
                    "mpa.mpa_id AS MPA_ID, " +
                    "mpa.name AS MPA_NAME " +
                    "FROM films " +
                    "LEFT JOIN mpa ON films.film_id = mpa.mpa_id " +
                    "WHERE films.film_id = :film_id";


    private final NamedParameterJdbcOperations jdbc;


    private final MpaRepository mpaRepository;
    private final FilmGenreRepository filmGenreRepository;


    @Override
    public Collection<Film> getAllFilms() {
        return List.of();
    }

    @Override
    public Film getFilmById(Long id) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("film_id", id);

        // используем try/catch, чтобы корректно обрабатывать случай, если в БД нет записи по id
        try {
            LinkedHashSet<Genre> genreList = filmGenreRepository.getAllFilmGenresById(id);
            Film film = jdbc.queryForObject(FIND_BY_ID, sqlParameterSource, new FilmRowMapper());
            if (film != null) {
                film.setGenres(genreList);
            }
            return film;

        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }
    }

    @Override
    public Film saveFilm(Film film) {
        // GeneratedKeyHolder для получения сгенерированного ключа из таблицы
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name", film.getName());
        sqlParameterSource.addValue("description", film.getDescription());
        sqlParameterSource.addValue("releaseDate", film.getReleaseDate());
        sqlParameterSource.addValue("duration", film.getDuration());
        sqlParameterSource.addValue("mpa_id", film.getMpaId());


        // вставляем данные в таблицу films
        jdbc.update("INSERT INTO films (name, description, releaseDate, duration, mpa_id) " +
                        "VALUES(:name, :description, :releaseDate, :duration, :mpa_id)",
                sqlParameterSource,
                keyHolder,
                new String[]{"film_id"});


        Number key = keyHolder.getKey();
        if (key != null) {
            film.setId(key.longValue());
        }

        return film;
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        return null;
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
}
