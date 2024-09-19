package ru.yandex.practicum.filmorate.repository.userRepo;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
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

        return newUser;
    }

    @Override
    public User updateUser(User updatedUser) {
        return userMap.put(updatedUser.getId(), updatedUser);
    }


    @Override
    public Set<Long> getAllUserFriendsIds(Long id) {
        return userFriendsList.get(id);
    }
















    /**
     * метод для генерации id-пользователя
     */
    private Long idGenerator() {
        return ++id;
    }

}
