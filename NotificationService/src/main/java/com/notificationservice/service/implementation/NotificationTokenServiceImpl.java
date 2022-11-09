package com.notificationservice.service.implementation;

import com.notificationservice.domain.NotificationToken;
import com.notificationservice.repository.NotificationRepository;
import com.notificationservice.service.NotificationTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "notificationTokenService")
public class NotificationTokenServiceImpl implements NotificationTokenService {
    @Resource
    private NotificationRepository notificationRepository;

    @Override
    public void register(NotificationToken token) {
        notificationRepository.add(token);
    }

    @Override
    public List<NotificationToken> getBy(String appKey) {
        return notificationRepository.findBy(appKey);
    }

    @Override
    public List<NotificationToken> get() {
        return notificationRepository.findAll();
    }
}
