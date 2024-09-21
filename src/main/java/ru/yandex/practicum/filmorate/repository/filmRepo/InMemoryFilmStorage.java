package ru.yandex.practicum.filmorate.repository.filmRepo;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Long id = 0L;
    private final Map<Long, Film> filmMap = new HashMap<>();

    // в Long мы храним id фильма
    // в Set<Long> мы будем хранить id пользователей, которые поставили фильму лайк
    private final Map<Long, Set<Long>> filmLikes = new HashMap<>();


    @Override
    public Collection<Film> getAllFilms() {
        return filmMap.values();
    }

    @Override
    public Film getFilmById(Long id) {
        return filmMap.get(id);
    }

    @Override
    public Film saveFilm(Film film) {

        film.setId(idGenerator());
        filmMap.put(film.getId(), film);

        isFilmSetExist(film.getId());

        return film;
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        return filmMap.put(updatedFilm.getId(), updatedFilm);
    }


    @Override
    public void addFilmLikeByUser(Long filmId, Long userId) {
        filmLikes.get(filmId).add(userId);
    }

    @Override
    public void deleteFilmLikeByUser(Long filmId, Long userId) {
        filmLikes.get(filmId).remove(userId);
    }

    @Override
    public Map<Long, Set<Long>> returnTopFilms() {
        return filmLikes;
    }

    /**
     * создаём для каждого фильма пустой список лайков
     */
    private void isFilmSetExist(Long filmId) {
        if (!filmLikes.containsKey(filmId)) {
            filmLikes.put(filmId, new HashSet<>());
        }
    }


    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }

}
