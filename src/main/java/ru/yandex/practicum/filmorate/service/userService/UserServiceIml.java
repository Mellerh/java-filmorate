package ru.yandex.practicum.filmorate.service.userService;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.friendshipRepo.FriendshipRepository;
import ru.yandex.practicum.filmorate.repository.userRepo.UserRepository;

import java.util.*;

/**
 * сервис для реализации логики по добавлению/апДейту User
 */

@Slf4j
@Service
public class UserServiceIml implements UserService {

    @Autowired
    @Qualifier("jdbcUserRepository")
    private UserRepository userRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;


    @Override
    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User createNewUser(User newUser) {
        return userRepository.saveUser(newUser);
    }

    @Override
    public User updateUser(User updatedUser) {
        User userToUpdate = userRepository.getUserById(updatedUser.getId());
        if (userToUpdate == null) {
            throw new NotFoundException("Пользователь с " + updatedUser.getId() + " не найден.");
        }

        return userRepository.updateUser(updatedUser);
    }

    @Override
    public User getUserById(Long userId) {

        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден.");
        }

        return user;
    }


    /**
     * возвращаем список всех друзей пользователя
     * SRP - принцип единой ответственности
     */
    @Override
    public void addFriend(Long userId, Long friendId) {
        if (userId == friendId) {
            throw new ValidationException("Нельзя добавить самого себя в друзья!");
        }

        User user = userRepository.getUserById(userId);
        User friend = userRepository.getUserById(friendId);
        if (user == null || friend == null) {
            throw new NotFoundException("Не удалось добавить пользователя с " + userId + " и пользователя " + friendId);
        }

        friendshipRepository.addNewFriend(userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        if (userId == friendId) {
            throw new ValidationException("Нельзя удалить самого себя из друзей!");
        }
        friendshipRepository.deleteFriend(userId, friendId);
    }

    @Override
    public List<User> getFriends(Long userId) {
        List<User> friends = new ArrayList<>();
        if (userId != null) {
            friends = friendshipRepository.getFriends(userId);
        }
        return friends;
    }

    @Override
    public List<User> getCommonFriends(Long firstUserId, Long secondUserId) {

        User firstUser = userRepository.getUserById(firstUserId);
        User secondUser = userRepository.getUserById(secondUserId);
        Set<User> intersection = null;

        if ((firstUser != null) && (secondUser != null)) {
            intersection = new HashSet<>(friendshipRepository.getFriends(firstUserId));
            intersection.retainAll(friendshipRepository.getFriends(secondUserId));
        }
        return new ArrayList<User>(intersection);
    }


}
