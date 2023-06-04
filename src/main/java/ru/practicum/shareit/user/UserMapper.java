package ru.practicum.shareit.user;

import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static User makeUser(UserDto userDto) throws BadRequestException {
        User user = new User();
        user.setName(userDto.getName());
//        if (userDto.getEmail()==(null)) {
//            throw new BadRequestException("Ошибка в поле email (User)");
//        }
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static UserDto makeUserDto(User user) throws BadRequestException {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
//        if (user.getEmail().equals(null)) {
//            throw new BadRequestException("Ошибка в поле email (User)");
//        }
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
