package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.BadRequestException;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(Long userId, BookingEntity bookingEntity) throws BadRequestException;

    BookingDto bookingStatus(Long userId, Long bookingId, Boolean approve) throws BadRequestException;

    BookingDto getBooking(Long userId, Long id);

    List<BookingDto> getBookingsOwner(Long id, String state) throws BadRequestException;

    List<BookingDto> getBookingState(Long id, String state) throws BadRequestException;
}
