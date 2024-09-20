package ru.yandex.practicum.filmorate.repository.filmRepo;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> getAllFilms();

    Film getFilmById(Long id);

    Film saveFilm(Film film);

    Film updateFilm(Film updatedFilm);
}
