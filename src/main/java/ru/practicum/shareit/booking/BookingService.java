package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    Booking toBooking(BookingDto booking);

    Booking bookingStatus(BookingDto bookingDto, BookingStatus book);

    Booking getBooking(Long id);

    List<Booking> getAllBooking(Long id);

    List<Booking> getAllBookingState();
}
