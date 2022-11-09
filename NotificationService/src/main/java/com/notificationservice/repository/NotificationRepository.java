package com.notificationservice.repository;

import com.notificationservice.domain.NotificationToken;

import java.util.List;

public interface NotificationRepository {

    public void add(NotificationToken token);
    public List<String> getAppKeys();

    public List<String> getTokens(final String appKey);

    public NotificationToken findBy(final String appKey, final String serviceToken);

    public List<NotificationToken> findBy(final String appKey);

    public List<NotificationToken> findAll();
}
