package ru.yandex.practicum.filmorate.repository.userRepo;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository {

    Collection<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user);

    User updateUser(User user);

    List<User> getAllUserFriendsIds(Long id);

    void addNewFriendById(Long userId, Long friendId);

    void deleteFriendById(Long userId, Long friendId);

    List<User> getAllCommonFriends(Long id, Long otherId);
}
