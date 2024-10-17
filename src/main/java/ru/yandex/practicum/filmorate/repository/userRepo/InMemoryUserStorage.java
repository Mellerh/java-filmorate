package ru.yandex.practicum.filmorate.repository.userRepo;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

// используем название в Component, чтобы указывать его в @Qualifier для указания Spring, какую реалзиацию инжектить
@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {

    private Long id = 0L;
    private final Map<Long, User> userMap = new HashMap<>();
    private final Map<Long, Set<Long>> userFriendsList = new HashMap<>();


    @Override
    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    @Override
    public User getUserById(Long id) {
        return userMap.get(id);
    }

    @Override
    public User saveUser(User newUser) {
        newUser.setId(idGenerator());
        userMap.put(newUser.getId(), newUser);

        // при создании нового пользователя мы всегда создаём для него путой список друзей
        isFriendsSetExist(newUser.getId());

        return newUser;
    }

    @Override
    public User updateUser(User updatedUser) {
        return userMap.put(updatedUser.getId(), updatedUser);
    }



    @Override
    public List<User> getAllUserFriendsIds(Long id) {

        // возвращаем списко всех друзей пользователя
        return userFriendsList.get(id)
                .stream()
                .map(friendId -> getUserById(friendId))
                .filter(userFriend -> userFriend != null)
                .toList();
    }

    @Override
    public void addNewFriendById(Long userId, Long friendId) {
        userFriendsList.get(userId).add(friendId);
    }

    @Override
    public void deleteFriendById(Long userId, Long friendId) {
        // если у пользователя не окажется в списке друзей другой пользователь
        // remove просто вернёт false, если элемент не был найден
        // и не выбросит исключение.
        userFriendsList.get(userId).remove(friendId);
    }

    /**
     * возвращаем список друзей, общих с другим пользователем
     */
    @Override
    public List<User> getAllCommonFriends(Long id, Long otherId) {

        Set<Long> otherUserFriends = userFriendsList.get(otherId);

        // получаем список всех id-друзей первого пользователя и сравниваем с id из otherUserFriends
        return userFriendsList.get(id).stream()
                .filter(userFrId -> otherUserFriends.contains(userFrId))
                .map(commonFriendId -> getUserById(commonFriendId))
                .filter(user -> user != null)
                .toList();
    }


    /**
     * создаём для пользователя путой список друзей
     */
    private void isFriendsSetExist(Long userId) {
        if (!userFriendsList.containsKey(userId)) {
            userFriendsList.put(userId, new HashSet<>());
        }
    }


    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }

}
