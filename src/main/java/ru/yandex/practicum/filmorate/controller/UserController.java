package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Validated
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


    // мы создали сервис userService, который будет хранить пользователей и правильно их обновлять.
    // это позволяет поддерживать принцип единой ответственности
    private final UserService userService;

    /**
     * аннотация @Autowired автоматичски внедрит FilmService в контроллер
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createNewUser(@Valid @RequestBody User newUser) {
        return userService.createNewUser(newUser);
    }

    @PutMapping
    @Validated(Update.class)
    public User updateUser(@Valid @RequestBody User updatedUser) {
        return userService.updateUser(updatedUser);
    }


}
