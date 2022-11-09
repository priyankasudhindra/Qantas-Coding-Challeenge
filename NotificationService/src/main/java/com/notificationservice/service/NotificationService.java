package com.notificationservice.service;

import java.util.List;

public interface NotificationService {
    public void send(final List<String> serviceTokens, final String message);

    public void send(final String serviceToken, final String message);

}
