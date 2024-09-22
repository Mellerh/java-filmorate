package ru.yandex.practicum.filmorate.exception.exceptions;

public class DuplicatedDataException extends RuntimeException {

    public DuplicatedDataException(String massage) {
        super(massage);
    }

}
