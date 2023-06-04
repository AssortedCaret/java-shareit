package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    @Override
    public Booking toBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        return toBooking(bookingDto);
    }

    @Override
    public Booking bookingStatus(BookingDto bookingDto, BookingStatus book) {
        Booking booking = new Booking();
        booking = toBooking(bookingDto);
        booking.setStatus(book);
        return booking;
    }

    @Override
    public Booking getBooking(Long id) {
        return bookingRepository.getOne(id);
    }

    @Override
    public List<Booking> getAllBooking(Long id) {
        return null;//bookingRepository.findAllByUserIdContaining(id);
    }

    @Override
    public List<Booking> getAllBookingState() {
        return null;
    }
}
