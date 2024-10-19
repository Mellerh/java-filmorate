package ru.yandex.practicum.filmorate.repository.mpaRepo;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaRepository {

    Mpa getMpaById(Integer mpaId);

    Collection<Mpa> getAllMpa();

}
