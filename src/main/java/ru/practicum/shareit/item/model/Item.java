package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.ToString;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @JoinColumn(name = "owner")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    @NotBlank
    @Column(name = "name")
    private String name;
    @NotBlank
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @NotBlank
    @Column(name = "available")
    private Boolean available;
    @JoinColumn(name = "request")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemRequest request;
}
