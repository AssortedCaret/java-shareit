package ru.practicum.shareitgateway.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareitgateway.booking.dto.BookingStatus;
import ru.practicum.shareitgateway.user.User;
import ru.practicum.shareitgateway.item.ItemDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDto item;
    private User booker;
    private BookingStatus status;
}
