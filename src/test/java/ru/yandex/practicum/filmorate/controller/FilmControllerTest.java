package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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
        defaultFilmSettings();

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
        defaultFilmSettings();

        // получаем первый объект из коллекции
        filmService.addNewFilm(film);
        Collection<Film> allFilms = filmService.getAllFilms();
        Film filmToUpgrade = allFilms.iterator().next();

        // обновляем name и обновляем значение в сервисе хрения
        filmToUpgrade.setName("Рыжий Гарфилд");
        filmService.updateFilm(filmToUpgrade);
        Collection<Film> allFilms1 = filmService.getAllFilms();
        Film updatedFilm = allFilms.iterator().next();

        Assertions.assertEquals(1, updatedFilm.getId(), "idGenerator генерирует неккоректный id");
        Assertions.assertEquals("Рыжий Гарфилд", updatedFilm.getName(),
                "Некорректный update фильма по названию");

    }


    @Test
    @DisplayName("Проверяем корректность срабатывания исключения при неправлиьном id")
    void notFoundExceptionTest() {
        defaultFilmSettings();
        filmService.addNewFilm(film);

        film.setId(2L);

        Assertions.assertThrows(NotFoundException.class, () -> {
            filmService.updateFilm(film);
        }, "Должно быть выброшено исключения из-за отсутсвия Film с id 2");
    }


    private void defaultFilmSettings() {
        film.setName("Гарфилд");
        film.setDescription("рыжий кот");
        film.setReleaseDate(LocalDate.of(2010, 1, 1));
        film.setDuration(120);
    }

}