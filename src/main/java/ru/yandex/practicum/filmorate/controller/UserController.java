package ru.yandex.practicum.filmorate.controller;


import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    public User createNewUser(@RequestBody User user) {

        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {

        return user;
    }



    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }

}
