package ru.yandex.practicum.filmorate.service.genreService;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
public interface GenreService {

    Genre getGenreById(Integer id);

    Collection<Genre> getAllGenres();
}
