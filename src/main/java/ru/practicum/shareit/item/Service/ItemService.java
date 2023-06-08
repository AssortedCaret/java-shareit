package ru.practicum.shareit.item.Service;

import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItems(Long userId);

    ItemDto getItemById(Long userId, Long itemId);

    ItemDto getItemByIdSearch(Long itemId);

    List<ItemDto> getItemsText(String text);

    ItemDto createItem(Long userId, ItemDto itemDto) throws BadRequestException;

    ItemDto updateItemById(Long userId, Long id, ItemDto item) throws CloneNotSupportedException, BadRequestException;

    void deleteItemById(Long id);
}
