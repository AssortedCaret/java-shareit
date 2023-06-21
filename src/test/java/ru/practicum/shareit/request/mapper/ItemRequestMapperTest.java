package ru.practicum.shareit.request.mapper;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestMapperTest {
    private final ItemRequestMapper itemRequestMapper = new ItemRequestMapper();
    private final EasyRandom easyRandom = new EasyRandom();
    @Test
    void makeItemRequest() throws BadRequestException {
        ItemRequestDto itemRequestDto = easyRandom.nextObject(ItemRequestDto.class);
        ItemRequest itemRequest = itemRequestMapper.makeItemRequest(itemRequestDto);
        assertEquals(itemRequestDto.getId(), itemRequest.getId());
    }

    @Test
    void makeItemRequestDto() throws BadRequestException {
        ItemRequest itemRequest = easyRandom.nextObject(ItemRequest.class);
        ItemRequestDto itemRequestDto = itemRequestMapper.makeItemRequestDto(itemRequest);
        assertEquals(itemRequest.getId(), itemRequestDto.getId());
    }

    @Test
    void listToItemRequestDto() throws BadRequestException {
        ItemRequest itemRequest = easyRandom.nextObject(ItemRequest.class);
        List<ItemRequest> list = new ArrayList();
        list.add(itemRequest);
        List<ItemRequestDto> listDto = itemRequestMapper.listToItemRequestDto(list);
        assertEquals(list.size(), listDto.size());
    }
}