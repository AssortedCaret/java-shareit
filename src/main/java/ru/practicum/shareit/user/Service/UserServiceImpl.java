package ru.practicum.shareit.user.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private Integer id = 0;

    private final Map<Integer, User> userMap = new HashMap<>();
    private List<User> userList = new ArrayList<>();

    @Override
    public List<User> getUsers() {
        return new ArrayList<User>(userMap.values());
    }

    @Override
    public User getUserById(int id) {
        User user = userMap.get(id);
        return user;
    }

    @Override
    public User createUser(UserDto user) throws CloneNotSupportedException {
        User newUser = UserMapper.makeUser(user);
        for (Map.Entry<Integer, User> us : userMap.entrySet()) {
            User userM = us.getValue();
            if (userM.getEmail().equals(newUser.getEmail())) {
                throw new CloneNotSupportedException("Данный email уже зарегистрирован");
            }
        }
        if (newUser.getId() == null)
            newUser.setId(makeId());
        userMap.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public User updateUserById(int id, User user) throws CloneNotSupportedException {
        User adUser = userMap.get(id);
        if (user.getId() == null)
            user.setId(id);
        if (user.getEmail() == null)
            user.setEmail(adUser.getEmail());
        else {
            for (Map.Entry<Integer, User> us : userMap.entrySet()) {
                User userM = us.getValue();
                if (adUser.getId().compareTo(userM.getId()) != 0) {
                    if (userM.getEmail().equals(user.getEmail())) {
                        throw new CloneNotSupportedException("Данный email уже зарегистрирован");
                    }
                }
            }
        }
        if (user.getName() == null)
            user.setName(adUser.getName());
        userMap.put(id, user);
        return user;
    }

    @Override
    public void deleteUserById(int id) {
        userMap.remove(id);
    }

    private Integer makeId() {
        id += 1;
        return id;
    }
}
