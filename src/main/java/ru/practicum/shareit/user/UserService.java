package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {
    public List<User> getUsers();
    public User getUserById(int id);
    public User createUser(UserDto user) throws CloneNotSupportedException;
    public User updateUserById(int id, User user) throws CloneNotSupportedException;
    public void deleteUserById(int id);
}
