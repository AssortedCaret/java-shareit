package ru.practicum.shareit.item.Service;

import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItems(Long userId);

    ItemDto getItemById(Long id);

    List<Item> getItemsText(String text);

    ItemDto createItem(Long userId, ItemDto itemDto) throws BadRequestException;

    ItemDto updateItemById(Long userId, Long id, ItemDto item) throws CloneNotSupportedException, BadRequestException;

    void deleteItemById(int id);
}
