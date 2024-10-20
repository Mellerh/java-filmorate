package ru.yandex.practicum.filmorate.service.filmService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.filmLikesRepository.FilmLikesRepository;
import ru.yandex.practicum.filmorate.repository.filmRepo.FilmRepository;
import ru.yandex.practicum.filmorate.repository.mpaRepo.MpaRepository;
import ru.yandex.practicum.filmorate.repository.userRepo.UserRepository;

import java.util.Collection;


/**
 * сервис для реализации логики по добавлению/апДейту Film
 */

@Slf4j
@Service
public class FilmServiceIml implements FilmService {

    @Autowired
    @Qualifier("jdbcUserRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("jdbcFilmRepository")
    private FilmRepository filmRepository;

    @Autowired
    private MpaRepository mpaRepository;
    @Autowired
    private FilmLikesRepository filmLikesRepository;

    // с помощью @Qualifier явно указываем Спрингу, какую реализацию интерфейса инжектить




    @Override
    public Collection<Film> getAllFilms() {
        return filmRepository.getAllFilms();
    }

    @Override
    public Film addNewFilm(Film newFilm) {
        return filmRepository.saveFilm(newFilm);
    }


    @Override
    public Film updateFilm(Film updatedFilm) {
        // если фильма с переданным id нет в списке, выбрасываем исключение
        return filmRepository.updateFilm(updatedFilm);
    }


    // Работаем с конкретным фильмом по id
    @Override
    public Film getFilmById(Long id) {

        Film film = filmRepository.getFilmById(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + id + " не найден.");
        }

        return film;
    }

    @Override
    public void addFilmLikeByUser(Long filmId, Long userId) {
        Film film = filmRepository.getFilmById(filmId);
        if (film != null) {
            if (userRepository.getUserById(userId) != null) {
                filmLikesRepository.addLike(filmId, userId);
            } else {
                throw new NotFoundException("Пользователь c ID=" + userId + " не найден!");
            }
        } else {
            throw new NotFoundException("Фильм c ID=" + filmId + " не найден!");
        }
    }

    @Override
    public void deleteFilmLikeByUser(Long filmId, Long userId) {
        Film film = filmRepository.getFilmById(filmId);
        if (film != null) {
            if (film.getLikes().contains(userId)) {
                filmLikesRepository.deleteLike(filmId, userId);
            } else {
                throw new NotFoundException("Лайк от пользователя c ID=" + userId + " не найден!");
            }
        } else {
            throw new NotFoundException("Фильм c ID=" + filmId + " не найден!");
        }
    }

    @Override
    public Collection<Film> returnTopFilms(Integer count) {
        return filmLikesRepository.getPopular(count);
    }


}
