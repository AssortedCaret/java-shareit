package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.BadRequestException;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    @Autowired
    BookingService bookingService;

    @GetMapping("/owner")
    public List<BookingDto> getBookingsOwner(@RequestHeader("X-Sharer-User-Id") Long id,
                                             @RequestParam(defaultValue = "ALL") String state) throws BadRequestException {
        return bookingService.getBookingsOwner(id, state);
    }

    @GetMapping
    public List<BookingDto> getBookingState(@RequestHeader("X-Sharer-User-Id") Long id,
                                            @RequestParam(defaultValue = "ALL") String state) throws BadRequestException {
        return bookingService.getBookingState(id, state);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @PostMapping
    public BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody BookingEntity bookingEntity)
            throws BadRequestException {
        return bookingService.createBooking(userId, bookingEntity);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto bookingStatus(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @PathVariable Long bookingId, @RequestParam Boolean approved) throws BadRequestException {
        return bookingService.bookingStatus(userId, bookingId, approved);
    }
}
