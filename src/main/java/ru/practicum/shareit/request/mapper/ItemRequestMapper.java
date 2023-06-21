package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.ArrayList;
import java.util.List;

public class ItemRequestMapper {
    public static ItemRequest makeItemRequest(ItemRequestDto itemRequestDto) throws BadRequestException {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestDto.getId());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setRequester(itemRequestDto.getRequestor());
        itemRequest.setCreated(itemRequestDto.getCreated());
        return itemRequest;
    }

    public static ItemRequestDto makeItemRequestDto(ItemRequest itemRequest) throws BadRequestException {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setRequestor(itemRequest.getRequester());
        itemRequestDto.setCreated(itemRequest.getCreated());
        return itemRequestDto;
    }

    public static List<ItemRequestDto> listToItemRequestDto(List<ItemRequest> itemRequest) throws BadRequestException {
        List<ItemRequestDto> dtos = new ArrayList<>();
        for (ItemRequest request : itemRequest) {
            dtos.add(makeItemRequestDto(request));
        }
        return dtos;
    }
}