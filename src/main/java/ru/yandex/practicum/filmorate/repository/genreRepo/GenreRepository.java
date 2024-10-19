package ru.yandex.practicum.filmorate.repository.genreRepo;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Repository
public interface GenreRepository {

    Genre getGenreById(Integer id);

    Collection<Genre> getAllGenres();

}
