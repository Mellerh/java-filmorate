package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.Update;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Validated
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    Long id = 0L;
    Map<Long, User> userMap = new HashMap<>();

    @GetMapping
    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    @PostMapping
    public User createNewUser(@Valid @RequestBody User newUser) {
        log.info("Создаём пользователя" + newUser.getLogin());

        newUser.setId(idGenerator());
        userMap.put(newUser.getId(), newUser);

        log.info("Пользователь" + newUser.getLogin() + " создан. Его id - " + newUser.getId());
        return newUser;
    }

    @PutMapping
    @Validated(Update.class)
    public User updateUser(@Valid @RequestBody User updatedUser) {
        log.info("Обновляем пользователя " + updatedUser.getId());

        User userToUpdate = userMap.get(updatedUser.getId());
        if (userToUpdate == null) {
            log.warn("Пользователь с id " + updatedUser.getId() + " не найден.");
            throw new NotFoundException("Пользователь с " + updatedUser.getId() + " не найден.");
        }

        userToUpdate.setLogin(userToUpdate.getLogin());
        userToUpdate.setName(userToUpdate.getLogin());
        userToUpdate.setEmail(userToUpdate.getEmail());

        if (userToUpdate.getBirthday() != null) {
            userToUpdate.setBirthday(userToUpdate.getBirthday());
        }

        log.info("Пользователь " + updatedUser.getId() + "обновлён.");
        return updatedUser;
    }


    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }

}
