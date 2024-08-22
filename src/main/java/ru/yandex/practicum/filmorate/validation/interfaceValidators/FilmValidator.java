package ru.yandex.practicum.filmorate.validation.interfaceValidators;

import ru.yandex.practicum.filmorate.model.Film;

public class FilmValidator implements ValidationInterface<Film> {

    @Override
    public boolean postValidation(Film newFilm) {
        return true;
    }

    @Override
    public boolean putValidation(Film updatedFilm) {
        return true;
    }

}
