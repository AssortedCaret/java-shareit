package ru.practicum.shareit.item.mapper;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemMapperTest {
    private final ItemMapper itemMapper = new ItemMapper();
    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    void makeItem() {
        ItemDto itemDto = easyRandom.nextObject(ItemDto.class);
        Item item = itemMapper.makeItem(itemDto);
        assertEquals(itemDto.getId(), item.getId());
    }

    @Test
    void makeItemDto() {
        Item item = easyRandom.nextObject(Item.class);
        ItemDto itemDto = itemMapper.makeItemDto(item);
        assertEquals(itemDto.getId(), item.getId());
    }

    @Test
    void listToItemDto() throws BadRequestException {
        Item item = easyRandom.nextObject(Item.class);
        List<Item> list = new ArrayList();
        list.add(item);
        List<ItemDto> listDto = itemMapper.listToItemDto(list);
        assertEquals(list.size(), listDto.size());
    }
}