package ru.yandex.practicum.filmorate.repository.userRepo;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;


public interface UserRepository {

    Collection<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user);

    User updateUser(User user);
}
