package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Override
    Booking getOne(Long id);

    /**
     * для работы с параметром state
     **/
    @Query("select bok " +
            "from Booking as bok " +
            "where bok.booker.id like ?1 " +
            "order by bok.start desc")
    List<Booking> findAllByBookerIdOrderByDesc(Long id);// "ALL" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.booker.id like ?1 " +
            "and (bok.start like ?2 and bok.end like ?3) " +
            "order by bok.start desc")
    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByDesc(Long id, LocalDateTime start, LocalDateTime end); //"CURRENT" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.booker.id like ?1 " +
            "and (bok.end < ?2) " +
            "order by bok.start desc")
    List<Booking> findAllByBookerIdAndEndBeforeOrderByDesc(Long id, LocalDateTime end); //"PAST" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.booker.id like ?1 " +
            "and bok.start > ?2 " +
            "order by bok.start desc")
    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStartDesc(Long id, LocalDateTime now); //"FUTURE" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.booker.id like ?1 " +
            "and (bok.status like ?2) " +
            "order by bok.start desc")
    List<Booking> findAllByBookerIdAndBookerStatusWaitingOrderByDesc(Long id, BookingStatus status); //"WAITING" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.booker.id like ?1 " +
            "and (bok.status like ?2) " +
            "order by bok.start desc")
    List<Booking> findAllByBookerIdAndBookerStatusRejectedOrderByDesc(Long id, BookingStatus status); //"REJECTED" state

    /**
     * для работы с параметром owner
     **/

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.item.owner.id like ?1 " +
            "order by bok.start desc")
    List<Booking> findAllByBookerOwnerIdOrderByDesc(Long id);// "ALL" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.item.owner.id like ?1 " +
            "and (bok.start like ?2 and bok.end like ?3) " +
            "order by bok.start desc")
    List<Booking> findAllByBookerOwnerIdAndStartBeforeAndEndAfterOrderByDesc(Long id, LocalDateTime start, LocalDateTime end); //"CURRENT" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.item.owner.id like ?1 " +
            "and (bok.end < ?2) " +
            "order by bok.start desc")
    List<Booking> findAllByBookerOwnerIdAndEndBeforeOrderByDesc(Long id, LocalDateTime end); //"PAST" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.item.owner.id like ?1 " +
            "and bok.start > ?2 " +
            "order by bok.start desc")
    List<Booking> findAllByBookerOwnerIdAndBookerStartAfterOrderByDesc(Long id, LocalDateTime now); //"FUTURE" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.item.owner.id like ?1 " +
            "and (bok.status like ?2) " +
            "order by bok.start desc")
    List<Booking> findAllByBookerOwnerIdAndBookerStatusWaitingOrderByDesc(Long id, BookingStatus status); //"WAITING" state

    @Query("select bok " +
            "from Booking as bok " +
            "where bok.item.owner.id like ?1 " +
            "and (bok.status like ?2) " +
            "order by bok.start desc")
    List<Booking> findAllByBookerOwnerIdAndBookerStatusRejectedOrderByDesc(Long id, BookingStatus status); //"REJECTED" state

    @Query(value = "select * " +
            "from Bookings as bok " +
            "where bok.item_id = ?1 " +
            "and bok.start_date > ?2 " +
            "and bok.status = 'APPROVED' " +
            "order by bok.start_date asc " +
            "limit 1", nativeQuery = true)
    Optional<Booking> getNextBookingForItem(Long itemId, LocalDateTime now);

    @Query(value = "select * " +
            "from Bookings as bok " +
            "where bok.item_id = ?1 " +
            "and bok.start_date < ?2 " +
            "and bok.status = 'APPROVED' " +
            "order by bok.start_date asc " +
            "limit 1", nativeQuery = true)
    Optional<Booking> getLastBookingForItem(Long itemId, LocalDateTime now);
}
