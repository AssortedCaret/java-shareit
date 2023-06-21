package ru.practicum.shareit.request.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Entity
@Table(name = "requests")
public class ItemRequest {
    @Id
    private Long id;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "requester")
    @ManyToOne(fetch = FetchType.LAZY)
    private User requester;
    @DateTimeFormat
    private LocalDateTime created;
}
