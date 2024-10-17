package ru.yandex.practicum.filmorate.service.filmService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.filmRepo.FilmRepository;
import ru.yandex.practicum.filmorate.repository.userRepo.UserRepository;

import java.util.Collection;


/**
 * сервис для реализации логики по добавлению/апДейту Film
 */

@Slf4j
@Service
public class FilmServiceIml implements FilmService {

    @Autowired
    @Qualifier("jdbcFilmRepository")
    private FilmRepository filmStorage;

    @Autowired
    @Qualifier("jdbcUserRepository")
    private UserRepository userStorage;


    @Override
    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @Override
    public Film addNewFilm(Film newFilm) {
        return filmStorage.saveFilm(newFilm);
    }


    @Override
    public Film updateFilm(Film updatedFilm) {

        // если фильма с переданным id нет в списке, выбрасываем исключение
        Film filmToUpdate = filmStorage.getFilmById(updatedFilm.getId());
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

        return filmStorage.updateFilm(filmToUpdate);
    }


    // Работаем с конкретным фильмом по id
    @Override
    public Film getFilmById(Long id) {

        Film film = filmStorage.getFilmById(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + id + " не найден.");
        }

        return film;
    }

    @Override
    public void addFilmLikeByUser(Long filmId, Long userId) {
        validateUserAndFriend(filmId, userId);

        filmStorage.addFilmLikeByUser(filmId, userId);
    }

    @Override
    public void deleteFilmLikeByUser(Long filmId, Long userId) {
        validateUserAndFriend(filmId, userId);

        filmStorage.deleteFilmLikeByUser(filmId, userId);
    }

    @Override
    public Collection<Film> returnTopFilms(Long count) {
        if (count == null) {
            count = 10L;
        }

        return filmStorage.returnTopFilms(count);
    }


    /**
     * метод провряет, существуют ли филь и пользователя в репозитории
     */
    private void validateUserAndFriend(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден.");
        }

        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + filmId + " не найден.");
        }
    }

}
