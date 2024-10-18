package ru.yandex.practicum.filmorate.repository.genreRepo;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreRepository {

    List<Genre> getGenresByIds(List<Integer> ids);

}
