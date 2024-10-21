package ru.yandex.practicum.filmorate.repository.friendshipRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.userRepo.UserRepository;

import java.util.List;

@Repository
public class JdbcFriendshipRepository implements FriendshipRepository {

    @Qualifier("jdbcUserRepository")
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getFriends(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            String sql = "SELECT friendship.friend_id, users.email, users.login, users.name, users.birthday FROM friendship" +
                    " INNER JOIN users ON friendship.friend_id = users.user_id WHERE friendship.user_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                            rs.getLong("friend_id"),
                            rs.getString("email"),
                            rs.getString("login"),
                            rs.getString("name"),
                            rs.getDate("birthday").toLocalDate(),
                            null),
                    userId
            );
        } else {
            return null;
        }
    }

    @Override
    public void addNewFriend(Long userId, Long friendId) {

        if (friendshipExists(userId, friendId)) {
            String sql = "UPDATE friendship SET user_id = ? AND friend_id = ? " +
                    "WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(sql, friendId, userId, friendId, userId);
        }

        String sql = "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);

    }

    public boolean friendshipExists(Long userId, Long friendId) {
        String sql = "SELECT COUNT(*) FROM friendship WHERE user_id = ? AND friend_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, friendId);
        return count != null && count > 0;
    }


    @Override
    public void deleteFriend(Long userId, Long friendId) {
        User user = userRepository.getUserById(userId);
        User friend = userRepository.getUserById(friendId);
        if ((user != null) && (friend != null)) {
            String sql = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(sql, userId, friendId);
        }
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        String sql = "SELECT users.* FROM users " +
                "JOIN friendship AS friendship1 ON users.user_id = friendship1.friend_id AND friendship1.user_id = ? " +
                "JOIN friendship AS friendship2 ON users.user_id = friendship2.friend_id AND friendship2.user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                        rs.getLong("friend_id"),
                        rs.getString("email"),
                        rs.getString("login"),
                        rs.getString("name"),
                        rs.getDate("birthday").toLocalDate(),
                        null),
                id, otherId);
    }
}
