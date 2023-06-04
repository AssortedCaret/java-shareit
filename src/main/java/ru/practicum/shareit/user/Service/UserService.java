package ru.practicum.shareit.user.Service;

import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserById(Long id);

    User createUser(UserDto user) throws CloneNotSupportedException, BadRequestException;

    User updateUserById(Long id, UserDto user) throws CloneNotSupportedException, BadRequestException;

    void deleteUserById(Long id);
}
