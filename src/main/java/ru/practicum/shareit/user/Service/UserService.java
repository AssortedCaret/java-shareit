package ru.practicum.shareit.user.Service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserById(int id);

    User createUser(UserDto user) throws CloneNotSupportedException;

    User updateUserById(int id, User user) throws CloneNotSupportedException;

    void deleteUserById(int id);
}
