package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
public class Booking {
    @NonNull
    @DateTimeFormat
    private Date bookingDate;
    @NotBlank
    private Boolean bookingStatus;
    @Size(max = 200)
    private String bookingFeedback;
}
