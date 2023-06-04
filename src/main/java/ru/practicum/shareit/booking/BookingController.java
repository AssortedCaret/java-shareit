package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingDto;

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

    @GetMapping
    public Booking toBooking(BookingDto bookingDto) {
        return bookingService.toBooking(bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public Booking bookingStatus(BookingDto bookingDto, BookingStatus book) {
        return bookingService.bookingStatus(bookingDto, book);
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(Long id) {
        return bookingService.getBooking(id);
    }
/**
 *
 **/
    @GetMapping("/bookings?state={state}")
    public List<Booking> getAllBooking(Long id) {
        return bookingService.getAllBooking(id);
    }

    @GetMapping("/owner?state={state}")
    public List<Booking> getAllBookingState() {
        return bookingService.getAllBookingState();
    }
}
