package ru.yandex.practicum.filmorate.service.filmService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.filmRepo.FilmStorage;
import ru.yandex.practicum.filmorate.repository.filmRepo.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.repository.userRepo.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.repository.userRepo.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * сервис для реализации логики по добавлению/апДейту Film
 */

@Slf4j
@Service
public class FilmServiceIml implements FilmService {

    private final FilmStorage inMemoryFilmStorage;
    private final UserStorage inMemoryUserStorage;

    @Autowired
    public FilmServiceIml(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }


    @Override
    public Collection<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
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

        return inMemoryFilmStorage.updateFilm(filmToUpdate);
    }


    // Работаем с конкретным фильмом по id
    @Override
    public Film getFilmById(Long id) {

        Film film = inMemoryFilmStorage.getFilmById(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + id + " не найден.");
        }

        return film;
    }

    @Override
    public void addFilmLikeByUser(Long filmId, Long userId) {
        validateUserAndFriend(filmId, userId);

        inMemoryFilmStorage.addFilmLikeByUser(filmId, userId);
    }

    @Override
    public void deleteFilmLikeByUser(Long filmId, Long userId) {
        validateUserAndFriend(filmId, userId);

        inMemoryFilmStorage.deleteFilmLikeByUser(filmId, userId);
    }

    @Override
    public Collection<Film> returnTopFilms(Long count) {
        if (count == null) {
            count = 10L;
        }

        // получаем мапу фильмов с лайками
        Map<Long, Set<Long>> filmLikesMap = inMemoryFilmStorage.returnTopFilms();

        // сортируем фильмы по количеству лайков и ограничиваем результат значением count
        return filmLikesMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size())) // Сортируем по количеству лайков
                .limit(count) // Ограничиваем результат
                .map(entry -> inMemoryFilmStorage.getFilmById(entry.getKey())) // Получаем объекты фильмов по их id
                .filter(film -> film != null)
                .toList(); // Собираем в список
    }


    /**
     * метод провряет, существуют ли филь и пользователя в репозитории
     */
    private void validateUserAndFriend(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден.");
        }

        User user = inMemoryUserStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + filmId + " не найден.");
        }
    }

}
