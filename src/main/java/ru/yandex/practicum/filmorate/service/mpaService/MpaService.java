package ru.yandex.practicum.filmorate.service.mpaService;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Service
public interface MpaService {

    Mpa getMpaById(Integer mpaId);

    Mpa getMpaByIdWithCreation(Integer mpaId);
    Collection<Mpa> getAllMpa();

}
