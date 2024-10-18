package ru.yandex.practicum.filmorate.repository.filmRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

// используем аннотацию @Qualifier, чтобы указывать её в @FilmServiceIml для указания Spring, какую реалзиацию инжектить
@Component
@Qualifier("jdbcFilmRepository")
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {

    // интерфейс, позволяющий задавать именнованные параметры в SQL-запросы
    // реализация - NamedParameterJdbcTemplate
    private final NamedParameterJdbcOperations jdbc;


    @Override
    public Collection<Film> getAllFilms() {
        return List.of();
    }

    @Override
    public Film getFilmById(Long id) {
        return null;
    }

    @Override
    public Film saveFilm(Film film) {

        return null;
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
