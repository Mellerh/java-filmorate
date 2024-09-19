package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final FilmService filmService;

    /**
     * аннотация @Autowired автоматичски внедрит FilmService в контроллер
     */
    @Autowired
    public FilmController(FilmServiceIml filmService) {
        this.filmService = filmService;
    }


    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody Film newFilm) {
        return filmService.addNewFilm(newFilm);
    }


    @PutMapping
    @Validated(Update.class)
    public Film updateFilm(@Valid @RequestBody Film updatedFilm) {
        return filmService.updateFilm(updatedFilm);
    }


}
