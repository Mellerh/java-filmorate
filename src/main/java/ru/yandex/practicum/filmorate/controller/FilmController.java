package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    Long id = 0L;
    Map<Long, Film> filmMap = new HashMap<>();

    @GetMapping("/1")
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
