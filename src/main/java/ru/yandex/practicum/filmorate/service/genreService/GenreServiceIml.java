package ru.yandex.practicum.filmorate.service.genreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.genreRepo.GenreRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreServiceIml implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre getGenreById(Integer id) {
        Genre genre = genreRepository.getGenreById(id);
        if (genre == null) {
            throw new NotFoundException("Жанр с id " + id + " не найден.");
        }

        return genre;
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }
}
