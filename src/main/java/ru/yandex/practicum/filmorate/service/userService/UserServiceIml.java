package ru.yandex.practicum.filmorate.service.userService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.userRepo.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.repository.userRepo.UserStorage;

import java.util.Collection;
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

//        дополнительная логика, дублирующая логику в моделе User
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
     * вся логика должна нахоидтся в сервисе, поэтому поиск друзей реализован тут.
     * SRP - принцип единой ответственности
     */
    @Override
    public Collection<User> getAllUserFriends(Long id) {
        User user = inMemoryUserStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        // снова обращаемся в сервис и возвращаем списко всех друзей пользователя
        // возвращаем List<User>
        return inMemoryUserStorage.getAllUserFriendsIds(id).stream()
                .map(friendId -> inMemoryUserStorage.getUserById(friendId))
                .filter(userFriend -> userFriend != null)
                .toList();
    }

    @Override
    public void addNewFriendById(Long id, Long friendId) {
        // провреяем, существуют ли пользователи в репозитории
        validateUserAndFriend(id, friendId);

        // добавляем друга пользователю и наоборот
        inMemoryUserStorage.addNewFriendById(id, friendId);
        inMemoryUserStorage.addNewFriendById(friendId, id);

    }

    @Override
    public void deleteFriendById(Long id, Long friendId) {
        validateUserAndFriend(id, friendId);

        // проверяем, есть один друг в списке у другого
        if (!inMemoryUserStorage.getAllUserFriendsIds(id).contains(friendId)) {
            throw new NotFoundException("Пользователь с id " + friendId + " не является другом пользователю с id " + id);
        }

        inMemoryUserStorage.deleteFriendById(id, friendId);
        inMemoryUserStorage.deleteFriendById(friendId, id);
    }

    /**
     * возвращаем список друзей, общих с другим пользователем
     */
    @Override
    public Collection<User> getAllCommonFriends(Long id, Long otherId) {

        validateUserAndFriend(id, otherId);

        Set<Long> userFriends = inMemoryUserStorage.getAllUserFriendsIds(id);
        Set<Long> otherUserFriends = inMemoryUserStorage.getAllUserFriendsIds(otherId);

        return userFriends.stream()
                .filter(userFrId -> otherUserFriends.contains(userFrId))
                .map(commonFriendId -> inMemoryUserStorage.getUserById(commonFriendId))
                .filter(user -> user != null)
                .toList();

    }


    /**
     * метод провряет, существуют ли пользователи в репозитории
     */
    private void validateUserAndFriend(Long id, Long friendId) {
        User user = inMemoryUserStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        User userFriend = inMemoryUserStorage.getUserById(friendId);
        if (userFriend == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }
    }

}
