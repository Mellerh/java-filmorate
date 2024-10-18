package ru.yandex.practicum.filmorate.repository.filmRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
        // GeneratedKeyHolder для получения сгенерированного ключа из таблицы
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        // получаем id mpa для сохранение в таблицу film
        // вариант, если с клиента приходит не id mpa, а название
//        Integer mpa_id= jdbc.update("SELECT mpa_id FROM mpa WHERE name = :name",
//                new MapSqlParameterSource("name", film.getMpa()));

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name", film.getName());
        sqlParameterSource.addValue("description", film.getDescription());
        sqlParameterSource.addValue("releaseDate", film.getReleaseDate());
        sqlParameterSource.addValue("duration", film.getDuration());
        sqlParameterSource.addValue("mpa_id", film.getMpa());


        // вставляем данные в таблицу films
        jdbc.update("INSERT INTO films (name, description, releaseDate, duration, mpa_id) " +
                        "VALUES(:name, :description, :releaseDate, :duration, :mpa_id)",
                sqlParameterSource,
                keyHolder,
                new String[]{"film_id"});


        film.setId(keyHolder.getKeyAs(Long.class));
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
