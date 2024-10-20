package ru.yandex.practicum.filmorate.repository.filmLikesRepository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmLikesRepository {

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Film> getPopular(Integer count);

    List<Long> getLikes(Long filmId);

}
