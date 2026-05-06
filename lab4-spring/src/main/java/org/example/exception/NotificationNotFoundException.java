package org.example.exception;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(Long id) {
        super("Уведомление с id=" + id + " не найдено");
    }
}
