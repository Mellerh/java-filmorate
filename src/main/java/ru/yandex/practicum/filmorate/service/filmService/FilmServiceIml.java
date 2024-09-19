package ru.yandex.practicum.filmorate.service.filmService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.filmRepo.FilmStorage;
import ru.yandex.practicum.filmorate.repository.filmRepo.InMemoryFilmStorage;

import java.util.Collection;


/**
 * сервис для реализации логики по добавлению/апДейту Film
 */

@Slf4j
@Service
public class FilmServiceIml implements FilmService {

    private final FilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmServiceIml(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }


    @Override
    public Collection<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @Override
    public Film getFilmById(Long id) {

        Film film = inMemoryFilmStorage.getFilmById(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + id + " не найден.");
        }

        return film;
    }

    @Override
    public Film addNewFilm(Film newFilm) {
        return inMemoryFilmStorage.saveFilm(newFilm);
    }


    @Override
    public Film updateFilm(Film updatedFilm) {

        // если фильма с переданным id нет в списке, выбрасываем исключение
        Film filmToUpdate = inMemoryFilmStorage.getFilmById(updatedFilm.getId());
        if (filmToUpdate == null) {
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


}
