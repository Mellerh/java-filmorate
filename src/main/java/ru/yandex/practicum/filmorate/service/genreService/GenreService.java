package ru.yandex.practicum.filmorate.service.genreService;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;

@Service
public interface GenreService {

    Genre getGenreById(Integer id);

    void putGenres(Film film);

    Set<Genre> getFilmGenres(Long filmId);
    Collection<Genre> getAllGenres();
}
