package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.service.filmService.FilmService;
import ru.yandex.practicum.filmorate.service.filmService.FilmServiceIml;

import java.util.Collection;

@Validated
@RestController
@RequestMapping("/films")
public class FilmController {

    // мы создали сервис FilmService, который будет хранить фильмы и правильно их обновлять.
    // это позволяет поддерживать принцип единой ответственности
    private final FilmService filmServiceIml;

    /**
     * аннотация @Autowired автоматичски внедрит FilmService в контроллер
     */
    @Autowired
    public FilmController(FilmServiceIml filmServiceIml) {
        this.filmServiceIml = filmService;
    }



    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmServiceIml.getAllFilms();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addNewFilm(@Valid @RequestBody Film newFilm) {
        return filmServiceIml.addNewFilm(newFilm);
    }

    @PutMapping
    @Validated(Update.class)
    public Film updateFilm(@Valid @RequestBody Film updatedFilm) {
        return filmServiceIml.updateFilm(updatedFilm);
    }



    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmServiceIml.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addFilmLikeByUser(@PathVariable Long id, @PathVariable Long userId) {
        filmServiceIml.addFilmLikeByUser(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteFilmLikeByUser(@PathVariable Long id, @PathVariable Long userId) {
        filmServiceIml.deleteFilmLikeByUser(id, userId);
    }


}
