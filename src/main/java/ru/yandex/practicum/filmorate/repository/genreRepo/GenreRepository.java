package ru.yandex.practicum.filmorate.repository.genreRepo;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;
import java.util.List;

@Repository
public interface GenreRepository {
    List<Genre> getGenres();

    List<Genre> getAllGenresByIds(LinkedHashSet<Integer> genreIds);

    Genre getGenreById(Integer id);

    void delete(Film film);

    void add(Film film);

    List<Genre> getFilmGenres(Long filmId);

}
