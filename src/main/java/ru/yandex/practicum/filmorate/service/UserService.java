package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * класс для хранения пользователей и реализации логики по их добавлению/апДейту
 */

@Slf4j
@Service
public class UserService {

    Long id = 0L;
    Map<Long, User> userMap = new HashMap<>();

    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    public User createNewUser(User newUser) {
        newUser.setId(idGenerator());
        userMap.put(newUser.getId(), newUser);

        return newUser;
    }

    public User updateUser(User updatedUser) {

        User userToUpdate = userMap.get(updatedUser.getId());
        if (userToUpdate == null) {
            log.warn("Пользователь с id " + updatedUser.getId() + " не найден.");
            throw new NotFoundException("Пользователь с " + updatedUser.getId() + " не найден.");
        }

        userToUpdate.setLogin(updatedUser.getLogin());
        userToUpdate.setEmail(updatedUser.getEmail());


        if (updatedUser.getName() != null) {
            userToUpdate.setName(updatedUser.getName());
        } else {
            userToUpdate.setName(updatedUser.getLogin());
        }

        if (userToUpdate.getBirthday() != null) {
            userToUpdate.setBirthday(updatedUser.getBirthday());
        }

        return userToUpdate;
    }


    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }

}
