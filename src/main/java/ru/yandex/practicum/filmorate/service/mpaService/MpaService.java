package ru.yandex.practicum.filmorate.service.mpaService;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Component
public interface MpaService {

    Mpa getMpaById(Integer mpaId);

    Collection<Mpa> getAllMpa();

}