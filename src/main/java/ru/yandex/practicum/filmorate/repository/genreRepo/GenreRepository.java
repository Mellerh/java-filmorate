package ru.yandex.practicum.filmorate.repository.genreRepo;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreRepository {

    Genre getGenreById(Integer id);

    Collection<Genre> getAllGenres();

}
