package ru.yandex.practicum.filmorate.repository.friendshipRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.userRepo.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        User user = userRepository.getUserById(userId);
        User friend = userRepository.getUserById(friendId);
        if ((user != null) && (friend != null)) {
            if (friend.getFriends().contains(userId)) {
                String sql = "UPDATE friendship SET user_id = ? AND friend_id = ? " +
                        "WHERE user_id = ? AND friend_id = ?";
                jdbcTemplate.update(sql, friendId, userId, friendId, userId);
            }
            String sql = "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, userId, friendId);
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        User user = userRepository.getUserById(userId);
        User friend = userRepository.getUserById(friendId);
        if ((user != null) && (friend != null)) {
            String sql = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(sql, userId, friendId);

            if (friend.getFriends().contains(userId)) {
                // дружба стала невзаимной - нужно поменять статус
                sql = "UPDATE friendship SET user_id = ? AND friend_id = ? " +
                        "WHERE user_id = ? AND friend_id = ?";
                jdbcTemplate.update(sql, friendId, userId, false, friendId, userId);
            }
        }
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        User firstUser = userRepository.getUserById(id);
        User secondUser = userRepository.getUserById(otherId);
        Set<User> intersection = null;

        if ((firstUser != null) && (secondUser != null)) {
            intersection = new HashSet<>(getFriends(id));
            intersection.retainAll(getFriends(otherId));
        }
        return new ArrayList<User>(intersection);
    }
}
