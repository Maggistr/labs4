package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class NotificationManager {

    private final Map<String, org.example.service.MessageService> messageServices;

    @Autowired
    public NotificationManager(Map<String, org.example.service.MessageService> messageServices) {
        this.messageServices = messageServices;
    }


    public void notify(String message, String recipient, String serviceType) {
        org.example.service.MessageService service = messageServices.get(serviceType);
        if (service != null) {
            if (service instanceof org.example.service.AdvancedMessageService advanced) {
                System.out.println("Тип сервиса: " + advanced.getServiceType());
            }
            service.sendMessage(message, recipient);
        } else {
            System.out.println("Сервис '" + serviceType + "' не найден. " + "\nДоступные: " + messageServices.keySet());
        }
    }
}