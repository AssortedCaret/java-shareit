package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.Service.ItemService;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@RequestMapping("/items")
@Component
@RequiredArgsConstructor
public class ItemController {
    @Autowired
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsText(@RequestParam String text) {
        return itemService.getItemsText(text);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id) {
        return itemService.getItemById(userId, id);
    }

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemDto itemDto)
            throws BadRequestException {
        log.info("User добавлен(UserController)");
        return itemService.createItem(userId, itemDto);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestBody CommentDto commentDto, @RequestHeader("X-Sharer-User-Id") Long userId,
                                    @PathVariable Long itemId) throws BadRequestException {
        log.info("User добавлен(UserController)");
        return itemService.createComment(commentDto, userId, itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateUserByIdPatch(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId,
                                       @RequestBody ItemDto item) throws BadRequestException, CloneNotSupportedException {
        return itemService.updateItemById(userId, itemId, item);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        itemService.deleteItemById(id);
    }
}
