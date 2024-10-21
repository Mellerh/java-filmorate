package ru.yandex.practicum.filmorate.repository.filmRepo;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmRepository {

    Collection<Film> getAllFilms();

    Film getFilmById(Long id);

    Film saveFilm(Film film);

    Film updateFilm(Film updatedFilm);

    void addFilmLikeByUser(Long filmId, Long userId);

    void deleteFilmLikeByUser(Long filmId, Long userId);

    Collection<Film> returnTopFilms(Long count);
}
