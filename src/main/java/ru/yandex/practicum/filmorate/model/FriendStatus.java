package ru.yandex.practicum.filmorate.model;


/**
 * статус для связи «дружба» между двумя пользователями:
 * UNCONFIRMED — когда один пользователь отправил запрос на добавление другого пользователя в друзья,
 * CONFIRMED — когда второй пользователь согласился на добавление.
 */
public enum FriendStatus {

    UNCONFIRMED,
    CONFIRMED

}
