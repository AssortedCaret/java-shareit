package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class ItemMapper {
    public static Item makeItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setRequest(itemDto.getRequest());
        return item;
    }

    public static ItemDto makeItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setOwner(item.getOwner().getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setRequest(item.getRequest());
        return itemDto;
    }
}
