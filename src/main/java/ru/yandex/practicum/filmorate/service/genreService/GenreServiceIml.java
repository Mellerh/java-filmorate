package ru.yandex.practicum.filmorate.service.genreService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotCorrectFieldException;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.genreRepo.JbdcGenreRepository;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreServiceIml implements GenreService {

    @Autowired
    private JbdcGenreRepository genreRepository;

    @Override
    public Collection<Genre> getAllGenres() {
        return genreRepository.getGenres();
    }

    @Override
    public Genre getGenreById(Integer id) {
        Genre genre = genreRepository.getGenreById(id);
        if (genre == null) {
            throw new NotFoundException("Genre с id " + id + " не найден.");
        }
        return genre;
    }

    @Override
    public Genre getGenreByIdWithCreation(Integer id) {
        Genre genre = genreRepository.getGenreById(id);
        if (genre == null) {
            throw new NotCorrectFieldException("Genre с id " + id + " не найден.");
        }
        return genre;
    }

    @Override
    public List<Genre> getAllGenresByIds(Set<Integer> genreIds) {
        return genreRepository.getAllGenresByIds(genreIds);
    }


    @Override
    public void putGenres(Film film) {
        genreRepository.delete(film);
        genreRepository.add(film);
    }

    @Override
    public LinkedHashSet<Genre> getFilmGenres(Long filmId) {
        return new LinkedHashSet<>(genreRepository.getFilmGenres(filmId));
    }
}
