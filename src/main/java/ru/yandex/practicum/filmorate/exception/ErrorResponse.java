package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * объект, описывающий ответ сервера на возникающий ошибки. он позволяет задать всем ответам одинаковую структуру
 */

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String error;
    private String description;

}
