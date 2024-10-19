package ru.yandex.practicum.filmorate.repository.filmGenreRepo;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;

public interface FilmGenreRepository {

    LinkedHashSet<Genre> getAllFilmGenresById(Long filmId);

    void addFilmGenre(Long filmId, Long genreId);

    void deleteFilmGenre(Long genreId, Long filmId);


}
