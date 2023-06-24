package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@Component
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @GetMapping
    public List<ItemRequestDto> getRequests(@RequestHeader("X-Sharer-User-Id") Long userId) throws BadRequestException {
        return itemRequestService.getRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getRequestsFrom(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "5") Integer size) throws BadRequestException {
        return itemRequestService.getRequestsFrom(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestsById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) throws BadRequestException {
        return itemRequestService.getRequestsById(userId, requestId);
    }

    @PostMapping
    public ItemRequestDto createRequests(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemRequestDto itemRequestDto) throws BadRequestException {
        return itemRequestService.createRequests(userId, itemRequestDto);
    }
}
