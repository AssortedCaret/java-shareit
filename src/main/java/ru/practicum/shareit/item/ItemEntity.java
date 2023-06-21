package ru.practicum.shareit.item;

import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

public class ItemEntity {
    private Long id;
    private Long owner;
    private String name;
}
