package ru.yandex.practicum.filmorate.service.userService;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    Collection<User> getAllUsers();

    User getUserById(Long id);

    User createNewUser(User newUser);

    User updateUser(User updatedUser);
}
