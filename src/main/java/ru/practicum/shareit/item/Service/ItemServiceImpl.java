package ru.practicum.shareit.item.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.BadRequestException;
import ru.practicum.shareit.item.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.Service.UserService;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.practicum.shareit.item.ItemMapper.makeItem;
import static ru.practicum.shareit.item.ItemMapper.makeItemDto;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private Integer id = 0;
    private Map<Integer, Item> itemMap = new HashMap<>();

    @Override
    public List<Item> getItems(int userId) {
        List<Item> itemList = new ArrayList<>();
        for (Map.Entry<Integer, Item> it: itemMap.entrySet()) {
            Item itemM = it.getValue();
            if (itemM.getOwner().getId()== userId)
                itemList.add(itemM);
        }
        return itemList;
    }

    @Override
    public ItemDto getItemById(int id) {
        Item item = itemMap.get(id);
        return makeItemDto(item);
    }

    @Override
    public ItemDto createItem(int userId, ItemDto itemDto) throws BadRequestException {
        if (userService.getUserById(userId) == null)
            throw new NotFoundException("Поле User отсутствует");
        itemDto.setOwner(userService.getUserById(userId));
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
    public ItemDto updateItemById(int userId, int id, ItemDto itemDto) {
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

    private Integer makeId(){
        id += 1;
        return id;
    }
}
