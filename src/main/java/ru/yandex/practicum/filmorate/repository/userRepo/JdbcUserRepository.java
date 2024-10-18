package ru.yandex.practicum.filmorate.repository.userRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Component
@Qualifier("jdbcUserRepository")
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Collection<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public List<User> getAllUserFriendsIds(Long id) {
        return List.of();
    }

    @Override
    public void addNewFriendById(Long userId, Long friendId) {

    }

    @Override
    public void deleteFriendById(Long userId, Long friendId) {

    }

    @Override
    public List<User> getAllCommonFriends(Long id, Long otherId) {
        return List.of();
    }
}
