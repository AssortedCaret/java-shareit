package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.user.Service.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@RequestMapping("/users")
@Component
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws BadRequestException {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody UserDto user) throws CloneNotSupportedException, BadRequestException {
        log.info("User добавлен(UserController)");
        return userService.createUser(user);
    }

    @PatchMapping("/{userId}")
    public User updateUserByIdPatch(@PathVariable Long userId, @RequestBody UserDto user)
            throws CloneNotSupportedException, BadRequestException {
        return userService.updateUserById(userId, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        log.info("User " + id + " удален(UserController)");
        userService.deleteUserById(id);
    }
}
