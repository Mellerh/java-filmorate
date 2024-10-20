package ru.yandex.practicum.filmorate.service.genreService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.genreRepo.JbdcGenreRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceIml implements GenreService {

    @Autowired
    private JbdcGenreRepository genreRepository;

    @Override
    public Collection<Genre> getAllGenres() {
        return genreRepository.getGenres().stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Genre getGenreById(Integer id) {
        return genreRepository.getGenreById(id);
    }

    @Override
    public void putGenres(Film film) {
        genreRepository.delete(film);
        genreRepository.add(film);
    }

    @Override
    public Set<Genre> getFilmGenres(Long filmId) {
        return new HashSet<>(genreRepository.getFilmGenres(filmId));
    }
}
