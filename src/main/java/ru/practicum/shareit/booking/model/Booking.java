package ru.practicum.shareit.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
/** для исправления косяка с InvalidDefinitionException: No serializer found for class
 * org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer**/
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Booking {
    @Id
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    @JoinColumn(name = "booker_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User booker;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
