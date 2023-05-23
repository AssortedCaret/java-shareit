package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.Service.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@Component
@RequiredArgsConstructor
public class ItemController {
    @Autowired
    private final ItemService itemService;

    @GetMapping("/items")
    public List<Item> getItems(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/items/{id}")
    public ItemDto getUserById(@PathVariable int id){
        return itemService.getItemById(id);
    }

    @PostMapping("/items")
    public ItemDto createUser(@RequestHeader("X-Sharer-User-Id") int userId, @RequestBody ItemDto itemDto)
            throws BadRequestException {
        log.info("User добавлен(UserController)");
        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/items/{itemId}")
    public ItemDto updateUserByIdPatch(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable int itemId,
                                       @RequestBody ItemDto item) throws BadRequestException, CloneNotSupportedException {
        return itemService.updateItemById(userId, itemId, item);
    }

    @DeleteMapping("/items/{id}")
    public void deleteUserById(@PathVariable int id){
        itemService.deleteItemById(id);
    }
}
