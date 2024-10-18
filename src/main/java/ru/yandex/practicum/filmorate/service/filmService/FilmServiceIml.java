package ru.yandex.practicum.filmorate.service.filmService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.filmRepo.FilmRepository;
import ru.yandex.practicum.filmorate.repository.genreRepo.GenreRepository;
import ru.yandex.practicum.filmorate.repository.mpa.MpaRepository;
import ru.yandex.practicum.filmorate.repository.userRepo.UserRepository;

import java.util.Collection;
import java.util.List;


/**
 * сервис для реализации логики по добавлению/апДейту Film
 */

@Slf4j
@Service
public class FilmServiceIml implements FilmService {

    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final MpaRepository mpaRepository;

    // с помощью @Qualifier явно указываем Спрингу, какую реализацию интерфейса инжектить
    public FilmServiceIml(@Qualifier("jdbcFilmRepository") FilmRepository filmRepository,
                          @Qualifier("jdbcUserRepository") UserRepository userRepository,
                          GenreRepository genreRepository, MpaRepository mpaRepository) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.mpaRepository = mpaRepository;
    }



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
        Film filmToUpdate = filmRepository.getFilmById(updatedFilm.getId());
        if (filmToUpdate == null) {
            throw new NotFoundException("Фильм с id " + updatedFilm.getId() + " не найден.");
        }

        // получаем список id всех жанров
        List<Long> genreIds = filmToUpdate.getGenres().stream().map(Genre::getId).toList();

        // получаем

        filmToUpdate.setName(updatedFilm.getName());
        filmToUpdate.setReleaseDate(updatedFilm.getReleaseDate());

        if (updatedFilm.getDescription() != null) {
            filmToUpdate.setDescription(updatedFilm.getDescription());
        }

        if (updatedFilm.getDuration() != null) {
            filmToUpdate.setDuration(updatedFilm.getDuration());
        }

        return filmRepository.updateFilm(filmToUpdate);
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
        validateUserAndFriend(filmId, userId);

        filmRepository.addFilmLikeByUser(filmId, userId);
    }

    @Override
    public void deleteFilmLikeByUser(Long filmId, Long userId) {
        validateUserAndFriend(filmId, userId);

        filmRepository.deleteFilmLikeByUser(filmId, userId);
    }

    @Override
    public Collection<Film> returnTopFilms(Long count) {
        if (count == null) {
            count = 10L;
        }

        return filmRepository.returnTopFilms(count);
    }


    /**
     * метод провряет, существуют ли филь и пользователя в репозитории
     */
    private void validateUserAndFriend(Long filmId, Long userId) {
        Film film = filmRepository.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден.");
        }

        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + filmId + " не найден.");
        }
    }

}
