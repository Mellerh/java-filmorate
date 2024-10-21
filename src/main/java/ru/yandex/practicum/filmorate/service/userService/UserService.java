package ru.yandex.practicum.filmorate.service.userService;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Service
public interface UserService {
    Collection<User> getAllUsers();

    User createNewUser(User newUser);

    User updateUser(User updatedUser);

    User getUserById(Long id);

    Collection<User> getFriends(Long id);

    void addFriend(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId);

    Collection<User> getCommonFriends(Long id, Long otherId);
}
