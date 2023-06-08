package ru.practicum.shareit.item.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingItemEntity;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.Service.UserService;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.practicum.shareit.booking.BookingMapper.makeBookingDto;
import static ru.practicum.shareit.booking.BookingMapper.makeBookingItemEntity;
import static ru.practicum.shareit.item.mapper.ItemMapper.makeItem;
import static ru.practicum.shareit.item.mapper.ItemMapper.makeItemDto;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private Long id = 0L;
    private final Map<Long, Item> itemMap = new HashMap<>();

    @Override
    public List<ItemDto> getItems(Long userId) {
        List<ItemDto> itemList = new ArrayList<>();
        ItemDto itemDto = null;
        Booking next = null;
        Booking last = null;
        for (Item it : itemRepository.findAll()) {
            if (it.getOwner().getId().equals(userId)) {
                next = bookingRepository.getNextBookingForItem(it.getId(), LocalDateTime.now()).orElse(null);
                last = bookingRepository.getLastBookingForItem(it.getId(), LocalDateTime.now()).orElse(null);
            }
            BookingItemEntity nextDto = new BookingItemEntity();
            BookingItemEntity lastDto = new BookingItemEntity();
            if (next != null)
                nextDto = makeBookingItemEntity(next);
            else nextDto = null;
            if (last != null)
                lastDto = makeBookingItemEntity(last);
            else lastDto = null;
            itemDto = makeItemDto(it);
            itemDto.setNextBooking(nextDto);
            itemDto.setLastBooking(lastDto);
            if (it.getOwner().getId() == userId)
                itemList.add(itemDto);
        }
        return itemList;
    }

    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        Item item = new Item();
        if (itemId <= id)
            item = itemRepository.getById(itemId);
        else
            throw new NotFoundException("Заданного Item id не существует");
        Booking next = null;
        Booking last = null;
        if (item.getOwner().getId().equals(userId)) {
            next = bookingRepository.getNextBookingForItem(item.getId(), LocalDateTime.now()).orElse(null);
            last = bookingRepository.getLastBookingForItem(item.getId(), LocalDateTime.now()).orElse(null);
        }
        BookingItemEntity nextDto = null;
        BookingItemEntity lastDto = null;
        if (next != null) 
            nextDto = makeBookingItemEntity(next);
        if (last != null)
            lastDto = makeBookingItemEntity(last);
        ItemDto itemDto = makeItemDto(item);
        itemDto.setNextBooking(nextDto);
        itemDto.setLastBooking(lastDto);
        return itemDto;
    }

    public ItemDto getItemByIdSearch(Long itemId) {
        Item item = new Item();
        if (itemId <= id)
            item = itemRepository.getById(itemId);
        else
            throw new NotFoundException("Заданного Item id не существует");
        ItemDto itemDto = makeItemDto(item);
        return itemDto;
    }

    @Override
    public List<ItemDto> getItemsText(String text) {
        List<ItemDto> itemList = new ArrayList<>();
        String checkingTheComparisonName;
        String checkingTheComparisonDescription;
        if (text == null || text.equals(""))
            return new ArrayList<>();
        else {
            for (Item it : itemRepository.findAll()) {
                Item itemM = it;
                if (itemM.getAvailable()) {
                    checkingTheComparisonName = itemM.getName().toLowerCase();
                    checkingTheComparisonDescription = itemM.getDescription().toLowerCase();
                    if (checkingTheComparisonName.contains(text.toLowerCase()))
                        itemList.add(makeItemDto(itemM));
                    else if (checkingTheComparisonDescription.contains(text.toLowerCase()))
                        itemList.add(makeItemDto(itemM));
                }
            }
            return itemList;
        }
    }

    @Transactional
    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) throws BadRequestException {
        User owner = userRepository.getById(userId);
        if (itemDto.getAvailable() == null)
            throw new BadRequestException("Поле Available отсутствует");
        if (itemDto.getName() == null || itemDto.getName() == "")
            throw new BadRequestException("Поле Name отсутствует");
        if (itemDto.getDescription() == null)
            throw new BadRequestException("Поле Description отсутствует");
        if (userService.getUserById(userId) == null)
            throw new NotFoundException("Поле User отсутствует");
        itemDto.setId(makeId());
        Item item = makeItem(itemDto);
        item.setId(itemDto.getId());
        item.setOwner(owner);
        itemMap.put(item.getId(), item);
        itemRepository.save(item);
        return itemDto;
    }

    @Transactional
    @Override
    public ItemDto updateItemById(Long userId, Long id, ItemDto itemDto) {
        Item adItem = itemRepository.getById(id);
        User verificationUser = adItem.getOwner();
        if (verificationUser.getId() != userId) {
            throw new NotFoundException("Поле Owner не совпадает");
        }
        if (itemDto.getId() != null)
            adItem.setId(id);
        if (itemDto.getName() != null)
            adItem.setName(itemDto.getName());
        if (itemDto.getDescription() != null)
            adItem.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null)
            adItem.setAvailable(itemDto.getAvailable());
        itemMap.put(id, adItem);
        itemRepository.save(adItem);
        itemDto = makeItemDto(adItem);
        return itemDto;
    }

    @Override
    public void deleteItemById(Long id) {
        itemMap.remove(id);
        itemRepository.delete(itemRepository.getById(id));
    }

    public Long returnId(){
        return id;
    }

    private Long makeId() {
        id += 1;
        return id;
    }
}
