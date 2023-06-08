package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.Service.ItemServiceImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.Service.UserServiceImpl;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.booking.BookingMapper.listToBookingDto;
import static ru.practicum.shareit.booking.BookingMapper.makeBookingDto;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserServiceImpl userService;
    private final ItemServiceImpl itemService;

    Long id = 0L;

    @Override
    public BookingDto createBooking(Long userId, BookingEntity bookingEntity) throws BadRequestException {
        if (userId > userService.returnId()) {
            throw new NotFoundException("Данного юзера не существует (Booking.create)");
        }
        User user = userRepository.getById(userId);
        if (bookingEntity.getItemId() > itemService.returnId()) {
            throw new NotFoundException("Данной вещи не существует (Booking.create)");
        }
        Item item = itemRepository.getById(bookingEntity.getItemId());
        if (userId == item.getOwner().getId()) {
            throw new NotFoundException("Пользователь является собственником вещи (Booking.create)");
        }
        if (!item.getAvailable()) {
            throw new BadRequestException("Данную вещь нельзя забронировать (Booking.create)");
        }
//        LocalDateTime end = bookingEntity.getEnd();
        if (bookingEntity.getStart() == null ||
                bookingEntity.getEnd() == null || bookingEntity.getEnd().isBefore(LocalDateTime.now().minusMinutes(5)) ||
                bookingEntity.getStart().isBefore(LocalDateTime.now().minusMinutes(5)) ||
                bookingEntity.getEnd().isBefore(bookingEntity.getStart()) ||
                bookingEntity.getEnd().equals(bookingEntity.getStart()))
            throw new BadRequestException("Время окончания бронирования указано не верно (Booking.create)");
        Booking booking = new Booking();
        booking.setBooker(user);
        booking.setId(makeId());
        booking.setItem(item);
        booking.setStart(bookingEntity.getStart());
        booking.setEnd(bookingEntity.getEnd());
        booking.setStatus(BookingStatus.WAITING);
        BookingDto bookingDto = makeBookingDto(booking);
        bookingRepository.save(booking);
        return bookingDto;
    }

    @Override
    public BookingDto bookingStatus(Long userId, Long bookingId, Boolean approve) throws BadRequestException {
        Booking booking = bookingRepository.getById(bookingId);
        if (booking.getItem().getOwner().getId() != userId)
            throw new NotFoundException("Подтверждать статус имеет права только собственник вещи(Booking.status)");
        if (booking.getStatus().equals(BookingStatus.APPROVED)) {
            throw new BadRequestException("Статус уже подтвержден(Booking.status)");
        }
        if (approve == true && booking.getStatus().equals(BookingStatus.WAITING))
            booking.setStatus(BookingStatus.APPROVED);
        else
            booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return makeBookingDto(booking);
    }

    @Override
    public BookingDto getBooking(Long userId, Long bookerId) {
        Booking booking = bookingRepository.getById(bookerId);
        if (userId > userService.returnId()) {
            throw new NotFoundException("Данного юзера не существует (Booking.create)");
        }
        if (bookerId > id)
            throw new NotFoundException("Данная бронь отсутствует(Booking.get)");
        if (booking.getItem().getOwner().getId() != userId && booking.getBooker().getId() != userId) {
            throw new NotFoundException("Вы не являетесь собственником");
        }
        return makeBookingDto(booking);
    }

    @Override
    public List<BookingDto> getBookingsOwner(Long userId, String state) throws BadRequestException {
        if (userId > userService.returnId()) {
            throw new NotFoundException("Данного юзера не существует (Booking.create)");
        }
        switch (state) {
            case "ALL":
                return listToBookingDto(bookingRepository.findAllByBookerOwnerIdOrderByDesc(userId));
            case "CURRENT":
                return listToBookingDto(bookingRepository.findAllByBookerOwnerIdAndStartBeforeAndEndAfterOrderByDesc(userId, LocalDateTime.now(),
                        LocalDateTime.now()));
            case "PAST":
                return listToBookingDto(bookingRepository.findAllByBookerOwnerIdAndEndBeforeOrderByDesc(userId, LocalDateTime.now()));
            case "FUTURE":
                return listToBookingDto(bookingRepository.findAllByBookerOwnerIdAndBookerStartAfterOrderByDesc(userId, LocalDateTime.now()));
            case "WAITING":
                return listToBookingDto(bookingRepository.findAllByBookerOwnerIdAndBookerStatusWaitingOrderByDesc(userId, BookingStatus.WAITING));
            case "REJECTED":
                return listToBookingDto(bookingRepository.findAllByBookerOwnerIdAndBookerStatusRejectedOrderByDesc(userId, BookingStatus.REJECTED));
            case "UNSUPPORTED_STATUS":
                throw new BadRequestException("Unknown state: UNSUPPORTED_STATUS");
        }
        return null;
    }

    @Override
    public List<BookingDto> getBookingState(Long userId, String state) throws BadRequestException {
        List<BookingDto> booking = new ArrayList<>();
        if (userId > userService.returnId()) {
            throw new NotFoundException("Данного юзера не существует (Booking.create)");
        }
        switch (state) {
            case "ALL":
                return listToBookingDto(bookingRepository.findAllByBookerIdOrderByDesc(userId));
            case "CURRENT":
                return listToBookingDto(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByDesc(userId, LocalDateTime.now(),
                        LocalDateTime.now()));
            case "PAST":
                return listToBookingDto(bookingRepository.findAllByBookerIdAndEndBeforeOrderByDesc(userId, LocalDateTime.now()));
            case "FUTURE":
                return listToBookingDto(bookingRepository.findAllByBookerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now()));
            case "WAITING":
                return listToBookingDto(bookingRepository.findAllByBookerIdAndBookerStatusWaitingOrderByDesc(userId, BookingStatus.WAITING));
            case "REJECTED":
                return listToBookingDto(bookingRepository.findAllByBookerIdAndBookerStatusRejectedOrderByDesc(userId, BookingStatus.REJECTED));
            case "UNSUPPORTED_STATUS":
                throw new BadRequestException("Unknown state: UNSUPPORTED_STATUS");
        }
        return null;
    }

    private Long makeId() {
        id += 1;
        return id;
    }
}
