package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Override
    Item getOne(Long id);

    Item save(Item item);

    Item getById(Long id);

    @Query("select it " +
            "from Item as it " +
            "where it.owner.id like ?1 ")
    List<Item> findAllItemWhereOwner(Long userId);
}
