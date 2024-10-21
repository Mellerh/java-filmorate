package ru.yandex.practicum.filmorate.service.filmService;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Service
public interface FilmService {
    Collection<Film> getAllFilms();

    Film getFilmById(Long id);

    Film addNewFilm(Film newFilm);

    Film updateFilm(Film updatedFilm);

    void addFilmLikeByUser(Long id, Long userId);

    void deleteFilmLikeByUser(Long id, Long userId);

    Collection<Film> returnTopFilms(Integer count);
}
