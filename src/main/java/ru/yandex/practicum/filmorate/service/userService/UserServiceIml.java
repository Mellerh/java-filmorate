package ru.yandex.practicum.filmorate.service.userService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.userRepo.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.repository.userRepo.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * сервис для реализации логики по добавлению/апДейту User
 */

@Slf4j
@Service
public class UserServiceIml implements UserService {

    private final UserStorage inMemoryUserStorage;

    @Autowired
    public UserServiceIml(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }


    @Override
    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @Override
    public User createNewUser(User newUser) {
        return inMemoryUserStorage.saveUser(newUser);
    }

    @Override
    public User updateUser(User updatedUser) {
//        log.info("Update User: {} - Started", updatedUser);

        User userToUpdate = inMemoryUserStorage.getUserById(updatedUser.getId());
        if (userToUpdate == null) {
            throw new NotFoundException("Пользователь с " + updatedUser.getId() + " не найден.");
        }

        userToUpdate.setLogin(updatedUser.getLogin());
        userToUpdate.setEmail(updatedUser.getEmail());

//
//        if (updatedUser.getName() != null) {
//            userToUpdate.setName(updatedUser.getName());
//        } else {
//            userToUpdate.setName(updatedUser.getLogin());
//        }

        if (userToUpdate.getBirthday() != null) {
            userToUpdate.setBirthday(updatedUser.getBirthday());
        }

        return inMemoryUserStorage.updateUser(updatedUser);
    }

    @Override
    public User getUserById(Long id) {

        User user = inMemoryUserStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        return user;
    }


    /**
     * возвращаем список всех друзей пользователя
     * вся логика находится в сервисе, поэтому поиск друзей реализован тут. принцип единой ответственности
     */
    @Override
    public Collection<User> getAllUserFriends(Long id) {
        // получаем список id всех друзей пользователя
        Set<Long> userFriendsIds = inMemoryUserStorage.getAllUserFriendsIds(id);

        // снова обращаемся в сервис и возвращаем списко всех друзей пользователя
        List<User> allUserFriends = userFriendsIds.stream()
                .map(friendId -> inMemoryUserStorage.getUserById(friendId))
                .filter(user -> user != null)
                .toList();

        return allUserFriends;
    }

    @Override
    public User addNewFriendById(Long id, Long friendId) {
        return null;
    }

    @Override
    public User deleteFriendById(Long id, Long friendId) {
        return null;
    }

    /**
     * возвращаем список друзей, общих с другим пользователем
     */
    @Override
    public Collection<User> getAllCommonFriends(Long id, Long otherId) {
        return List.of();
    }

}
