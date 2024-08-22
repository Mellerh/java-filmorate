package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * класс для хранения фильмов и реализации логики по их добавлению/апДейту
 */

@Slf4j
@Service
public class FilmService {

    Long id = 0L;
    Map<Long, Film> filmMap = new HashMap<>();


    public Collection<Film> getAllFilms() {
        return filmMap.values();
    }

    public Film addNewFilm(Film newFilm) {
        newFilm.setId(idGenerator());
        filmMap.put(newFilm.getId(), newFilm);

        return newFilm;
    }


    public Film updateFilm(Film updatedFilm) {

        // если фильма с переданным id нет в списке, выбрасываем исключение
        Film filmToUpdate = filmMap.get(updatedFilm.getId());
        if (filmToUpdate == null) {
            log.warn("Фильм с id " + updatedFilm.getId() + " не найден.");
            throw new NotFoundException("Фильм с id " + updatedFilm.getId() + " не найден.");
        }

        filmToUpdate.setName(updatedFilm.getName());
        filmToUpdate.setReleaseDate(updatedFilm.getReleaseDate());

        if (updatedFilm.getDescription() != null) {
            filmToUpdate.setDescription(updatedFilm.getDescription());
        }

        if (updatedFilm.getDuration() != null) {
            filmToUpdate.setDuration(updatedFilm.getDuration());
        }

        return filmToUpdate;
    }


    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }

}
