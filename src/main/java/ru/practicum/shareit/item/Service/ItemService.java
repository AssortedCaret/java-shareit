package ru.practicum.shareit.item.Service;

import ru.practicum.shareit.item.BadRequestException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    public List<Item> getItems( int userId);

    public ItemDto getItemById(int id);

    ItemDto createItem(int userId, ItemDto itemDto) throws BadRequestException;

    public ItemDto updateItemById(int userId, int id, ItemDto item) throws CloneNotSupportedException, BadRequestException;

    public void deleteItemById(int id);
}