package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.userService.UserService;
import ru.yandex.practicum.filmorate.service.userService.UserServiceIml;

import java.util.Collection;

@Validated
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
    public UserController(UserServiceIml userService) {
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


    // Работаем с конкретным пользователем по id

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getAllUserFriends(@PathVariable Long id) {
        return userService.getAllUserFriends(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addNewFriendById(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.addNewFriendById(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriendById(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.deleteFriendById(id, friendId);
    }

    /**
     * возвращаем список друзей, общих с другим пользователем
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getAllCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getAllCommonFriends(id, otherId);
    }
}
