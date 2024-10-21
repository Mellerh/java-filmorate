package ru.yandex.practicum.filmorate.service.mpaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotCorrectFieldException;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.mpaRepo.MpaRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaServiceIml implements MpaService {

    private final MpaRepository mpaRepository;

    @Override
    public Mpa getMpaById(Integer mpaId) {
        Mpa mpa = mpaRepository.getMpaById(mpaId);
        if (mpa == null) {
            throw new NotFoundException("Mpa с id " + mpaId + " не найден.");
        }

        return mpa;
    }

    @Override
    public Mpa getMpaByIdWithCreation(Integer mpaId) {
        Mpa mpa = mpaRepository.getMpaById(mpaId);
        if (mpa == null) {
            throw new NotCorrectFieldException("Mpa с id " + mpaId + " не найден.");
        }
        return mpa;
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return mpaRepository.getAllMpa();
    }
}
