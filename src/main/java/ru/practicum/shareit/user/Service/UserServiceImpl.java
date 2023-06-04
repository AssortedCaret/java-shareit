package ru.practicum.shareit.user.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Long id = 0L;
    private final Map<Long, User> userMap = new HashMap<>();
    private List<User> userList = new ArrayList<>();

    @Override
    public List<User> getUsers() {
        return new ArrayList<User>(userMap.values());
    }

    @Override
    public User getUserById(Long idUser) {
        if (idUser > id)
            throw new NotFoundException("Заданный Id отсутствует (User)");
        User user = userMap.get(idUser);
        return user;
    }

    @Override
    public User createUser(UserDto user) throws BadRequestException, CloneNotSupportedException {
        User newUser = UserMapper.makeUser(user);
        if (newUser.getEmail() == (null)) {
            throw new BadRequestException("Поле email не заполнено (User)");
        }
        for (Map.Entry<Long, User> us : userMap.entrySet()) {
            User userM = us.getValue();
            if (userM.getEmail().equals(newUser.getEmail())) {
                throw new CloneNotSupportedException("Такой email уже существует(User)");
            }
        }
        if (newUser.getId() == null)
            newUser.setId(makeId());
        if (!newUser.getEmail().contains("@")) {
            throw new BadRequestException("Неправильный email(User)");
        }
        if (newUser.getId() == null)
            newUser.setId(makeId());
        userMap.put(newUser.getId(), newUser);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User updateUserById(Long id, UserDto userDto) throws CloneNotSupportedException, BadRequestException {
        User adUser = UserMapper.makeUser(userDto);
        adUser.setId(id);
        if (!(userDto.getEmail() == null))
            adUser.setEmail(userDto.getEmail());
        else {
            adUser.setEmail(userRepository.getById(id).getEmail());
        }
//        else {
        for (Map.Entry<Long, User> us : userMap.entrySet()) {
            User userM = us.getValue();
            if (adUser.getId().compareTo(userM.getId()) != 0) {
                if (userM.getEmail().equals(adUser.getEmail())) {
                    throw new CloneNotSupportedException("Данный email уже зарегистрирован");
                }
            }
        }
//        }
        if (!(adUser.getName() == null))
            adUser.setName(userDto.getName());
        else {
            adUser.setName(userRepository.getById(id).getName());
        }
        userMap.put(id, adUser);
        userRepository.save(adUser);
        return adUser;
    }

    @Override
    public void deleteUserById(Long id) {
        userMap.remove(id);
        userRepository.delete(userRepository.getById(id));
    }

    private Long makeId() {
        id += 1;
        return id;
    }
}
