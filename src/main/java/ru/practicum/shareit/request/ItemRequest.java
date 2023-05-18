package ru.practicum.shareit.request;

import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-item-requests.
 */
public class ItemRequest {
    @NotBlank
    private String requestItem;
    @NotBlank
    private boolean statusRequestItem;
}
