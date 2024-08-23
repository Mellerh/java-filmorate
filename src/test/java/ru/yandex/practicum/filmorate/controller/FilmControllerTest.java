package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

class FilmControllerTest {


    // Если мы валидируем поля DTO через аннотации, то и в тестах нужно применять аннотации
    // подключаем валидатор
    private static Validator validator;

    // сервис для хренения фильмов
    private FilmService filmService;
    Film film;


    @BeforeAll
    @DisplayName("Общий валидатор")
    static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    @DisplayName("Инициализирующий метод")
    void init() {
        filmService = new FilmService();
        film = new Film();
    }


    @Test
    @DisplayName("Метод проверяет корректность создания фильма с валидными данными и добавлением в сервис хранеия")
    void validateFilmWithCorrectDate() {
        film.setName("Гарфилд");
        film.setDescription("рыжий кот");
        film.setReleaseDate(LocalDate.of(2010, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertTrue(violations.isEmpty(), "Ожидается, что валидация пройдёт успешно");

        filmService.addNewFilm(film);
        Assertions.assertEquals(1, filmService.getAllFilms().size(), "разрмер getAllFilms должно быть 1");
    }

    @Test
    @DisplayName("Метод проверяет корректность создания фильма с невалидными данными")
    void validateFilmWithUnCorrectDate() {
        film.setName("");
        film.setReleaseDate(LocalDate.of(2010, 1, 1));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Ожидается, что произойдёт ошибка валидации");
    }

    @Test
    @DisplayName("Метод проверяет корректность генерации id для фильма и последующего обновления фильма по id")
    void validateFilmIdAndUpdate() {
        film.setName("Гарфилд");
        film.setDescription("рыжий кот");
        film.setReleaseDate(LocalDate.of(2010, 1, 1));
        film.setDuration(120);

        Assertions.assertEquals(1, film.getId(), "idGenerator генерирует неккоректный id");

        // получаем первый объект из коллекции
        Collection<Film> allFilms = filmService.getAllFilms();
        Film filmToUpgrade = allFilms.iterator().next();

        // TODO НУЖНО ПОМЕНЯТЬ ОБЪЕКТ ПО ЕГО id и проверить, что всё работает


        // вариант поиска по конкретному id.
//        Film filmToUpgrade = allFilms.stream()
//            .filter(film -> film.getId().equals(filmIdToFind))
//            .findFirst()
//            .orElse(null);
    }








    @Test
    @DisplayName("Метод провверяет корректность возращения фильмов")
    public void shouldReturnAllFilmsCorrectly() {
        Collection<Film> listOfFilms = filmService.getAllFilms();
        Assertions.assertEquals(0, listOfFilms.size(), "Метод getAllFilms должен возвращать 0");

        Film newFilm = new Film();

        filmService.addNewFilm(newFilm);
        Assertions.assertEquals(1, listOfFilms.size(), "Метод getAllFilms должен возвращать 1");

    }


}