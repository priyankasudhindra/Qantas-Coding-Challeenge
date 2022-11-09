package com.notificationservice.service;

import com.notificationservice.domain.NotificationToken;

import java.util.List;

public interface NotificationTokenService {
    void register(final NotificationToken token);
    List<NotificationToken> getBy(final String appKey);
    List<NotificationToken> get();
}
