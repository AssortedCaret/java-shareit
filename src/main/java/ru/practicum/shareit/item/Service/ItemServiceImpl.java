package ru.practicum.shareit.item.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.Service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.practicum.shareit.item.mapper.ItemMapper.makeItem;
import static ru.practicum.shareit.item.mapper.ItemMapper.makeItemDto;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private Long id = 0L;
    private final Map<Long, Item> itemMap = new HashMap<>();

    @Override
    public List<Item> getItems(Long userId) {
        List<Item> itemList = new ArrayList<>();
        for (Map.Entry<Long, Item> it : itemMap.entrySet()) {
            Item itemM = it.getValue();
            if (itemM.getOwner().getId() == userId)
                itemList.add(itemM);
        }
        return itemList;
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = itemMap.get(id);
        return makeItemDto(item);
    }

    @Override
    public List<Item> getItemsText(String text) {
        List<Item> itemList = new ArrayList<>();
        String checkingTheComparisonName;
        String checkingTheComparisonDescription;
        if (text == null || text.equals(""))
            return new ArrayList<>();
        else {
            for (Map.Entry<Long, Item> it : itemMap.entrySet()) {
                Item itemM = it.getValue();
                if (itemM.getAvailable()) {
                    checkingTheComparisonName = itemM.getName().toLowerCase();
                    checkingTheComparisonDescription = itemM.getDescription().toLowerCase();
                    if (checkingTheComparisonName.contains(text.toLowerCase()))
                        itemList.add(itemM);
                    else if (checkingTheComparisonDescription.contains(text.toLowerCase()))
                        itemList.add(itemM);
                }
            }
            return itemList;
        }
    }

    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) throws BadRequestException {
        if (userService.getUserById(userId) == null)
            throw new NotFoundException("Поле User отсутствует");
        itemDto.setOwner((long) userId);
        if (itemDto.getAvailable() == null)
            throw new BadRequestException("Поле Available отсутствует");
        if (itemDto.getName() == null || itemDto.getName() == "")
            throw new BadRequestException("Поле Name отсутствует");
        if (itemDto.getDescription() == null)
            throw new BadRequestException("Поле Description отсутствует");
        if (itemDto.getId() == null)
            itemDto.setId(makeId());
        itemMap.put(itemDto.getId(), makeItem(itemDto));
        return getItemById(itemDto.getId());
    }

    @Override
    public ItemDto updateItemById(Long userId, Long id, ItemDto itemDto) {
        Item adItem = itemMap.get(id);
        if (adItem.getOwner().getId() != userId) {
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
        return getItemById(adItem.getId());
    }

    @Override
    public void deleteItemById(int id) {
        itemMap.remove(id);
    }

    private Long makeId() {
        id += 1;
        return id;
    }
}
