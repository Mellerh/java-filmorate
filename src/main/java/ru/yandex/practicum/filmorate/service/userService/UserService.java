package ru.yandex.practicum.filmorate.service.userService;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    Collection<User> getAllUsers();

    User createNewUser(User newUser);

    User updateUser(User updatedUser);

    User getUserById(Long id);

    Collection<User> getAllUserFriends(Long id);

    void addNewFriendById(Long id, Long friendId);

    void deleteFriendById(Long id, Long friendId);

    Collection<User> getAllCommonFriends(Long id, Long otherId);
}
