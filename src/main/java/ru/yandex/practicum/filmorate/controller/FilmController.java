package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.helpres.Update;
import ru.yandex.practicum.filmorate.service.filmService.FilmService;

import java.util.Collection;

@Validated
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    // мы создали сервис FilmService, который будет хранить фильмы и правильно их обновлять.
    // это позволяет поддерживать принцип единой ответственности
    @Autowired
    private final FilmService filmService;


    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addNewFilm(@Valid @RequestBody Film newFilm) {
        return filmService.addNewFilm(newFilm);
    }

    @PutMapping
    @Validated(Update.class)
    public Film updateFilm(@Valid @RequestBody Film updatedFilm) {
        return filmService.updateFilm(updatedFilm);
    }


    // Работаем с конкретным фильмом по id
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addFilmLikeByUser(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addFilmLikeByUser(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteFilmLikeByUser(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteFilmLikeByUser(id, userId);
    }

    /**
     * возвращает список из первых count фильмов по количеству лайков.
     * Если значение параметра count не задано, вернёт первые 10
     * ?count={count}
     */
    @GetMapping("/popular")
    public Collection<Film> returnTopFilms(@RequestParam(required = false) Integer count) {
        return filmService.returnTopFilms(count);
    }


}
