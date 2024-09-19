package ru.yandex.practicum.filmorate.repository.filmRepo;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Long id = 0L;
    private final Map<Long, Film> filmMap = new HashMap<>();


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

        return film;
    }

    /// TODO ДОБАВИТЬ updateFilm по аналогии с User

    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }

}
