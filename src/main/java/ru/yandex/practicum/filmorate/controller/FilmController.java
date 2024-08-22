package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.Update;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Validated
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    Long id = 0L;
    Map<Long, Film> filmMap = new HashMap<>();

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmMap.values();
    }


    @PostMapping
    public Film addNewFilm(@Valid @RequestBody Film film) {
        log.info("Add Film: {} - Started", film);

        ///

        log.info("Add Film: {} - Finished", film);
        return film;
    }


    @PutMapping
    @Validated(Update.class)
    public Film updateFilm(@Valid @RequestBody Film film) {

        return film;
    }


    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }


}
