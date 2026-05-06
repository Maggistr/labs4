package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.NotificationDto;
import org.example.model.entity.Notification;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/add")
    public NotificationDto createNotification(@RequestBody @Valid NotificationDto request) {
        return mapToDto(notificationService.createNotification(request));
    }

    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications().stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        return mapToDto(notificationService.getNotificationById(id));
    }

    @PutMapping("/{id}")
    public NotificationDto updateNotification(@PathVariable Long id,
                                               @RequestBody @Valid NotificationDto request) {
        return mapToDto(notificationService.updateNotification(id, request));
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Уведомление удалено";
    }

    @GetMapping("/status/{status}")
    public List<NotificationDto> getByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatus(status).stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/channel/{channel}")
    public List<NotificationDto> getByChannel(@PathVariable NotificationChannel channel) {
        return notificationService.getNotificationsByChannel(channel).stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/recipient/{recipientId}")
    public List<NotificationDto> getByRecipientId(@PathVariable Long recipientId) {
        return notificationService.getNotificationsByRecipientId(recipientId).stream()
                .map(this::mapToDto)
                .toList();
    }

    // Часть 6.1: поиск по статусу и каналу
    @GetMapping("/filter")
    public List<NotificationDto> getByStatusAndChannel(
            @RequestParam NotificationStatus status,
            @RequestParam NotificationChannel channel) {
        return notificationService.getNotificationsByStatusAndChannel(status, channel).stream()
                .map(this::mapToDto)
                .toList();
    }

    // Часть 6.2: сортировка по дате создания (убывание)
    @GetMapping("/status/{status}/sorted")
    public List<NotificationDto> getByStatusSortedDesc(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatusSortedDesc(status).stream()
                .map(this::mapToDto)
                .toList();
    }

    // Самостоятельное задание: поиск по получателю и статусу
    @GetMapping("/recipient/{recipientId}/status/{status}")
    public List<NotificationDto> getByRecipientAndStatus(
            @PathVariable Long recipientId,
            @PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByRecipientAndStatus(recipientId, status).stream()
                .map(this::mapToDto)
                .toList();
    }

    // Самостоятельное задание: вынесен mapToDto
    private NotificationDto mapToDto(Notification notification) {
        return NotificationDto.builder()
                .title(notification.getTitle())
                .message(notification.getMessage())
                .channel(notification.getChannel())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .recipientId(notification.getRecipient().getId())
                .build();
    }
}
