package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.item.mapper.ItemMapper.makeItemDto;

@RequiredArgsConstructor
public class BookingMapper {
    private static ItemRepository itemRepository;
    private static UserRepository userRepository;

    public static Booking makeBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setStart(LocalDateTime.parse(bookingDto.getStart()));
        booking.setEnd(LocalDateTime.parse(bookingDto.getEnd()));
//        booking.setItem(bookingDto.getItem());
        return booking;
    }

    public static BookingDto makeBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart((booking.getStart().toString()));
        bookingDto.setEnd((booking.getEnd().toString()));
        bookingDto.setItem(makeItemDto(booking.getItem()));
        bookingDto.setBooker(booking.getBooker());
        bookingDto.setStatus(booking.getStatus());
        return bookingDto;
    }

    public static BookingItemEntity makeBookingItemEntity(Booking booking) {
        BookingItemEntity bookingItemEntity = new BookingItemEntity();
        bookingItemEntity.setId(booking.getId());
        bookingItemEntity.setBookerId(booking.getBooker().getId());
        return bookingItemEntity;
    }

//    public static BookingDto makeBookingDto(Booking booking) {
//        BookingDto bookingDto = new BookingDto();
//        bookingDto.setId(booking.getId());
//        bookingDto.setItemId(booking.getItem().getId());
//        bookingDto.setItemName(booking.getItem().getName());
//        bookingDto.setStart((booking.getStart().toString()));
//        bookingDto.setEnd((booking.getEnd().toString()));
//        bookingDto.setBookerId(booking.getBooker().getId());
//        bookingDto.setStatus(booking.getStatus());
//        return bookingDto;
//    }

    public static List<BookingDto> listToBookingDto(List<Booking> bookings) {
        List<BookingDto> dtos = new ArrayList<>();
        for (Booking booking : bookings) {
            dtos.add(makeBookingDto(booking));
        }
        return dtos;
    }
}
