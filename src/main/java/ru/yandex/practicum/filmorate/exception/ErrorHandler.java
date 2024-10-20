package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.exceptions.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.exceptions.NotCorrectFieldException;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;


/**
 * общий обработчик ошибок
 */

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.info("NotFoundException: {}", e.getMessage());
        return new ErrorResponse("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFoundException(final NotCorrectFieldException e) {
        log.info("NotCorrectFieldException: {}", e.getMessage());
        return new ErrorResponse("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedDataException(final DuplicatedDataException e) {
        log.info("DuplicatedDataException: {}", e.getMessage());
        return new ErrorResponse("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final MethodArgumentNotValidException e) {
        log.info("ValidationException: {}", e.getMessage());
        return new ErrorResponse("error", e.getMessage());
    }


    /**
     * непредвиденная ошибка на стороне сервера
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception e) {
        log.warn("ServerError", e);
        return new ErrorResponse("error", e.getMessage());
    }

}
