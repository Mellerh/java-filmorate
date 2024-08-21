package ru.yandex.practicum.filmorate.model;

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
    String name;
    String description;
    LocalDateTime releaseDate;
    int duration;

}
