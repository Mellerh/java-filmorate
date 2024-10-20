package ru.yandex.practicum.filmorate.repository.friendshipRepo;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipRepository {

    List<User> getFriends(Long userId);

    void addNewFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> getCommonFriends(Long id, Long otherId);

}
