package ru.yandex.practicum.filmorate.repository.genreRepo;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
public class JbdcGenreRepository implements GenreRepository {

    @Override
    public List<Genre> getGenresByIds(List<Integer> ids) {
        return List.of();
    }

}
