package ru.yandex.practicum.filmorate.repository.userRepo;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    Collection<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user);

    User updateUser(User user);

    Set<Long> getAllUserFriendsIds(Long id);
}