package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BookingMapper {
    private static ItemRepository itemRepository;
    private static UserRepository userRepository;

    public static Booking makeBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setStart(LocalDate.parse(bookingDto.getStart()));
        booking.setEnd(LocalDate.parse(bookingDto.getEnd()));
        booking.setItem(itemRepository.getOne(bookingDto.getItem()));
        return booking;
    }

    public static BookingDto makeBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart((booking.getStart().toString()));
        bookingDto.setEnd((booking.getEnd().toString()));
        bookingDto.setItem(itemRepository.getOne(bookingDto.getItem()).getId());
        bookingDto.setBooker(userRepository.getOne(bookingDto.getBooker()).getId());
        bookingDto.setStatus(booking.getStatus());
        return bookingDto;
    }

    public static List<BookingDto> mapToItemNoteDto(Iterable<Booking> bookings) {
        List<BookingDto> dtos = new ArrayList<>();
        for (Booking booking : bookings) {
            dtos.add(makeBookingDto(booking));
        }
        return dtos;
    }
}
