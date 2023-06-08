package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
/** для исправления косяка с InvalidDefinitionException: No serializer found for class
 * org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer**/
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Column(name = "available")
    private Boolean available;
    @JoinColumn(name = "request")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemRequest request;
}
