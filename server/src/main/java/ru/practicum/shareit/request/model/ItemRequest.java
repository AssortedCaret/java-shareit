package ru.practicum.shareit.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "created")
    private LocalDateTime created;
}
