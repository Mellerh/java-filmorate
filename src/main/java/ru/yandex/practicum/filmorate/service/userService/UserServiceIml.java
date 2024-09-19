package ru.yandex.practicum.filmorate.service.userService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.userRepo.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.repository.userRepo.UserStorage;

import java.util.Collection;

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
    public User getUserById(Long id) {

        User user = inMemoryUserStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        return user;
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


        if (updatedUser.getName() != null) {
            userToUpdate.setName(updatedUser.getName());
        } else {
            userToUpdate.setName(updatedUser.getLogin());
        }

        if (userToUpdate.getBirthday() != null) {
            userToUpdate.setBirthday(updatedUser.getBirthday());
        }

        return userToUpdate;
    }

}
