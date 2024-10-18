package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.yandex.practicum.filmorate.model.helpres.Update;

import java.time.LocalDate;
import java.util.LinkedHashSet;


@Data
@EqualsAndHashCode(of = "id")
public class Film {

    // делаем поле приватным, чтобы оно не попало в json-объект
    // а также помечаем аннотацией, чтобы не создавался геттер для этого поля, чтобы также не попало в json
    @Getter(AccessLevel.NONE)
    private final LocalDate filmMinReleaseDate = LocalDate.of(1895, 12, 18);

    // с помощью groups и маркерного интерфейса Update, мы проверяем наличие id только
    // на моменте PUT в контроллере
    @NotNull(groups = {Update.class})
    @Positive(groups = {Update.class})
    Long id;

    @NotBlank(message = "Название фильма не может быть пустым")
    String name;

    @Size(max = 200, message = "Длина описания фильма не может превышать 200 символов")
    String description;

    @NotNull(message = "Дата релиза фильма не может быть пустой")
    LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    Integer duration;

    LinkedHashSet<Genre> genres;

    // возрастной рейтинг фильма
    Mpa mpa;


    /**
     * валидация входных данных в контроллерах
     */
    @AssertTrue(message = "Дата релиза фильма должна быть после 18.12.1895")
    public boolean isValidReleaseDate() {
        return releaseDate.isAfter(filmMinReleaseDate);
    }

}


//{
//        "name": "Гарфилд",
//        "description": "Фильм про рыжего кота",
//        "releaseDate": "2008-10-10",
//        "duration": 88
//        }