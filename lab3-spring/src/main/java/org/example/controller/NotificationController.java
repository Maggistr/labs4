package org.example.controller;

import org.example.service.NotificationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final NotificationManager notificationManager;

    public NotificationController(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    @GetMapping("/notify")
    public String notify(@RequestParam(required = false) String message,
                         @RequestParam(defaultValue = "Не указан") String email,
                         @RequestParam(defaultValue = "customEmail") String via) {
        if (message == null) {
            message = "Сообщение пустое";
        }
        if (email.equals("Не указан")) {
            return "Укажите почту";
        }
        notificationManager.notify(message, email, via);
        return "Уведомление отправлено через: " + via;
    }
}