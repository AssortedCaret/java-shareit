package ru.practicum.shareit.item.Service;

import ru.practicum.shareit.item.BadRequestException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItems(int userId);

    ItemDto getItemById(int id);

    List<Item> getItemsText(String text);

    ItemDto createItem(int userId, ItemDto itemDto) throws BadRequestException;

    ItemDto updateItemById(int userId, int id, ItemDto item) throws CloneNotSupportedException, BadRequestException;

    void deleteItemById(int id);
}
