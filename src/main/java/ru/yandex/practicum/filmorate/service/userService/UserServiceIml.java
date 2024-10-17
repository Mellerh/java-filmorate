package ru.yandex.practicum.filmorate.service.userService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.userRepo.UserRepository;

import java.util.Collection;

/**
 * сервис для реализации логики по добавлению/апДейту User
 */

@Slf4j
@Service

public class UserServiceIml implements UserService {

    @Autowired
    @Qualifier("jdbcUserRepository")
    private UserRepository userStorage;



    @Override
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public User createNewUser(User newUser) {
        return userStorage.saveUser(newUser);
    }

    @Override
    public User updateUser(User updatedUser) {
//        log.info("Update User: {} - Started", updatedUser);

        User userToUpdate = userStorage.getUserById(updatedUser.getId());
        if (userToUpdate == null) {
            throw new NotFoundException("Пользователь с " + updatedUser.getId() + " не найден.");
        }

        userToUpdate.setLogin(updatedUser.getLogin());
        userToUpdate.setEmail(updatedUser.getEmail());

//        логика, дополняющая isNameValid() в User, для обновления полей пользователя
        if (updatedUser.getName() != null) {
            userToUpdate.setName(updatedUser.getName());
        } else {
            userToUpdate.setName(updatedUser.getLogin());
        }

        if (userToUpdate.getBirthday() != null) {
            userToUpdate.setBirthday(updatedUser.getBirthday());
        }

        return userStorage.updateUser(updatedUser);
    }

    @Override
    public User getUserById(Long id) {

        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        return user;
    }


    /**
     * возвращаем список всех друзей пользователя
     * SRP - принцип единой ответственности
     */
    @Override
    public Collection<User> getAllUserFriends(Long id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        return userStorage.getAllUserFriendsIds(id);
    }

    @Override
    public void addNewFriendById(Long id, Long friendId) {
        // провреяем, существуют ли пользователи в репозитории
        validateUserAndFriend(id, friendId);

        // добавляем друга пользователю и наоборот
        userStorage.addNewFriendById(id, friendId);
        userStorage.addNewFriendById(friendId, id);

    }

    @Override
    public void deleteFriendById(Long id, Long friendId) {
        validateUserAndFriend(id, friendId);


        // если у пользователя не окажется в списке друзей другой пользователь
        // remove просто вернёт false, если элемент не был найден
        // и не выбросит исключение.
        userStorage.deleteFriendById(id, friendId);
        userStorage.deleteFriendById(friendId, id);
    }

    /**
     * возвращаем список друзей, общих с другим пользователем
     */
    @Override
    public Collection<User> getAllCommonFriends(Long id, Long otherId) {

        validateUserAndFriend(id, otherId);

        return userStorage.getAllCommonFriends(id, otherId);
    }


    /**
     * метод провряет, существуют ли пользователи в репозитории
     */
    private void validateUserAndFriend(Long id, Long friendId) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        User userFriend = userStorage.getUserById(friendId);
        if (userFriend == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }
    }

}
