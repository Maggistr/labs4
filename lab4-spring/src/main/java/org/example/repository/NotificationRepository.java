package org.example.repository;

import org.example.model.entity.Notification;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Поиск по статусу
    List<Notification> findByStatus(NotificationStatus status);

    // Поиск по каналу
    List<Notification> findByChannel(NotificationChannel channel);

    // Поиск по получателю
    List<Notification> findByRecipientId(Long recipientId);

    // Часть 6.1: Поиск по статусу и каналу одновременно
    List<Notification> findByStatusAndChannel(NotificationStatus status, NotificationChannel channel);

    // Часть 6.2: Сортировка по дате создания (убывание) — самостоятельное задание
    List<Notification> findByStatusOrderByCreatedAtDesc(NotificationStatus status);

    // Часть 6.3: JPQL-запрос — поиск по статусу и каналу
    @Query("""
            select n
            from Notification n
            where n.status = :status
              and n.channel = :channel
            """)
    List<Notification> findByStatusAndChannelCustom(
            @Param("status") NotificationStatus status,
            @Param("channel") NotificationChannel channel
    );

    // Часть 6.3: Native SQL-запрос
    @Query(value = """
            select *
            from notifications
            where status = :status
              and channel = :channel
            """, nativeQuery = true)
    List<Notification> findNativeByStatusAndChannel(
            @Param("status") String status,
            @Param("channel") String channel
    );

    // Самостоятельное задание: поиск по recipientId и статусу (JPQL)
    @Query("""
            select n
            from Notification n
            where n.recipient.id = :recipientId
              and n.status = :status
            """)
    List<Notification> findByRecipientIdAndStatus(
            @Param("recipientId") Long recipientId,
            @Param("status") NotificationStatus status
    );
}
