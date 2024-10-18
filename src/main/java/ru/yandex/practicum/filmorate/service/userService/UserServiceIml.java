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
    private UserRepository userRepository;



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
//        log.info("Update User: {} - Started", updatedUser);

        User userToUpdate = userRepository.getUserById(updatedUser.getId());
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

        return userRepository.updateUser(updatedUser);
    }

    @Override
    public User getUserById(Long id) {

        User user = userRepository.getUserById(id);
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
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        return userRepository.getAllUserFriendsIds(id);
    }

    @Override
    public void addNewFriendById(Long id, Long friendId) {
        // провреяем, существуют ли пользователи в репозитории
        validateUserAndFriend(id, friendId);

        // добавляем друга пользователю и наоборот
        userRepository.addNewFriendById(id, friendId);
        userRepository.addNewFriendById(friendId, id);

    }

    @Override
    public void deleteFriendById(Long id, Long friendId) {
        validateUserAndFriend(id, friendId);


        // если у пользователя не окажется в списке друзей другой пользователь
        // remove просто вернёт false, если элемент не был найден
        // и не выбросит исключение.
        userRepository.deleteFriendById(id, friendId);
        userRepository.deleteFriendById(friendId, id);
    }

    /**
     * возвращаем список друзей, общих с другим пользователем
     */
    @Override
    public Collection<User> getAllCommonFriends(Long id, Long otherId) {

        validateUserAndFriend(id, otherId);

        return userRepository.getAllCommonFriends(id, otherId);
    }


    /**
     * метод провряет, существуют ли пользователи в репозитории
     */
    private void validateUserAndFriend(Long id, Long friendId) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }

        User userFriend = userRepository.getUserById(friendId);
        if (userFriend == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }
    }

}
