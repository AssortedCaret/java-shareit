package ru.practicum.shareit.request.service;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.Service.UserService;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.request.mapper.ItemRequestMapper.makeItemRequestDto;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {
    private ItemRequestService itemRequestService;
    private final EasyRandom easyRandom = new EasyRandom();
    private final ItemRequestMapper itemRequestMapper = new ItemRequestMapper();
    @Mock
    UserService userService;
    @Mock
    ItemRequestRepository itemRequestRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        itemRequestService = new ItemRequestServiceImpl(userService, itemRequestRepository,
                userRepository, itemRepository);
    }

    @Test
    void getRequests() throws BadRequestException {
        Long userId = 1L;
        when(userService.returnId())
                .thenReturn(userId);
        ItemRequest itemRequest = easyRandom.nextObject(ItemRequest.class);
        when(itemRequestRepository.getAllItemRequestForUser(anyLong()))
                .thenReturn(List.of(itemRequest));
        Item item = easyRandom.nextObject(Item.class);
        item.setRequestId(itemRequest);
        when(itemRepository.findAllItemWhereRequester(anyLong()))
                .thenReturn(List.of(item));
        List<ItemRequestDto> list = itemRequestService.getRequests(userId);
        List<ItemRequestDto> list1 = itemRequestMapper.listToItemRequestDto(List.of(itemRequest));
        assertEquals(list1.size(), list.size());
    }

    @Test
    void getRequestsById() throws BadRequestException {
        Long userId = 1L;
        when(userService.returnId())
                .thenReturn(userId);
        ItemRequest itemRequest = easyRandom.nextObject(ItemRequest.class);
        when(itemRequestRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(itemRequest));
        Item item = easyRandom.nextObject(Item.class);
        when(itemRepository.findAllItemWhereRequester(anyLong()))
                .thenReturn(List.of(item));
        ItemRequestDto newItemRequest = itemRequestService.getRequestsById(userId, itemRequest.getId());
        assertEquals(itemRequest.getId(), newItemRequest.getId());

    }

    @Test
    void createRequests() throws BadRequestException {
        Long userId = 1L;
        when(userService.returnId())
                .thenReturn(userId);
        ItemRequest itemRequest = easyRandom.nextObject(ItemRequest.class);
        ItemRequestDto itemRequestDto1 = makeItemRequestDto(itemRequest);
        User user = easyRandom.nextObject(User.class);
        when(userRepository.getById(anyLong()))
                .thenReturn(user);
        ItemRequestDto itemRequestDto = itemRequestService.createRequests(userId, itemRequestDto1);
        assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
    }
}