package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.validation.Update;

import java.time.LocalDate;


@Getter
@Setter
public class Film {

    final LocalDate FILM_MIN_RELEASE_DATE = LocalDate.of(1895, 12, 18);

    // с помощью groups и маркерного интерфейса Update, мы проверяем наличие id только
    // на моменте PUT в контроллере
    @NotBlank(groups = {Update.class})
    @Positive(groups = {Update.class})
    Long id;

    @NotBlank(message = "Название фильма не может быть пустым")
    String name;

    @Size(max = 200, message = "Длина описания фильма не может превышать 200 символов")
    String description;

    @NotNull(message = "Дата релиза фильма не может быть пустой")
    LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    int duration;


    @AssertTrue(message = "Дата релиза фильма должна быть после 18.12.1895")
    public boolean isValidReleaseDate() {
        return releaseDate.isAfter(FILM_MIN_RELEASE_DATE);
    }

}
