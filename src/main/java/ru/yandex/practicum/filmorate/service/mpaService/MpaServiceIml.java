package ru.yandex.practicum.filmorate.service.mpaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.mpaRepo.MpaRepository;

import java.util.Collection;
import java.util.List;

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
    public Collection<Mpa> getAllMpa() {
        return List.of();
    }
}
