package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Film.
 */
@Getter
@Setter
public class Film {

    Long id;

    @NotBlank(message = "Название не может быть пустым")
    String name;

    @Size(max = 200, message = "Длина не может превышать 200 символов")

            
    String description;

    LocalDateTime releaseDate;

    int duration;

}
