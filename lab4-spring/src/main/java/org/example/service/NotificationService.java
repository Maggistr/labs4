package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotificationNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.dto.NotificationDto;
import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.repository.NotificationRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public Notification createNotification(NotificationDto request) {
        User user = userRepository.findById(request.getRecipientId())
                .orElseThrow(() -> new UserNotFoundException(request.getRecipientId()));

        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());
        notification.setStatus(NotificationStatus.CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipient(user);

        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }

    @Transactional
    public Notification updateNotification(Long id, NotificationDto request) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));

        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());

        // Самостоятельное задание: при установке статуса SENT записываем sentAt
        if (request.getStatus() != null) {
            notification.setStatus(request.getStatus());
            if (request.getStatus() == NotificationStatus.SENT) {
                notification.setSentAt(LocalDateTime.now());
            }
        }

        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
        notificationRepository.delete(notification);
    }

    public List<Notification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    public List<Notification> getNotificationsByChannel(NotificationChannel channel) {
        return notificationRepository.findByChannel(channel);
    }

    public List<Notification> getNotificationsByRecipientId(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    // Часть 6.1: поиск по статусу и каналу
    public List<Notification> getNotificationsByStatusAndChannel(
            NotificationStatus status, NotificationChannel channel) {
        return notificationRepository.findByStatusAndChannel(status, channel);
    }

    // Часть 6.2: уведомления по статусу, отсортированные по дате создания (убывание)
    public List<Notification> getNotificationsByStatusSortedDesc(NotificationStatus status) {
        return notificationRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    // Самостоятельное задание: поиск по получателю и статусу через @Query
    public List<Notification> getNotificationsByRecipientAndStatus(
            Long recipientId, NotificationStatus status) {
        return notificationRepository.findByRecipientIdAndStatus(recipientId, status);
    }
}
