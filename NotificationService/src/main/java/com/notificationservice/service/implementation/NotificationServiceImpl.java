package com.notificationservice.service.implementation;

import com.notificationservice.domain.NotificationToken;
import com.notificationservice.repository.NotificationRepository;
import com.notificationservice.service.NotificationService;
import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.CommunicationException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "notificationService")
public class NotificationServiceImpl implements NotificationService {
    @Resource
    private NotificationRepository notificationRepository;
    @Value("${push.keyStore}")
    private String keyStore;
    @Value("${push.keyStorePassword}")
    private String keyStorePassword;
    private String keyStorePath;

    @PostConstruct
    private void init() {
        try {
            keyStorePath = Thread.currentThread().getContextClassLoader().getResource(keyStore).getPath();
        } catch (NullPointerException ex) {
            log.error(ex.getMessage());
        }
    }

    public void send(final List<String> serviceTokens, final String message) {
        sendMessage(message, serviceTokens);
    }

    public void send(final String serviceToken, final String message) {
        List<String> tokens = new ArrayList<>();
        tokens.add(serviceToken);
        sendMessage(message, tokens);
    }

    public void broadcast(final String appKey, final String message) {
        send(notificationRepository.getTokens(appKey), message);
    }

    public void broadcast(final String message) {
        for (NotificationToken token : notificationRepository.findAll()) {
            send(token.getServiceToken(), message);
        }
    }

    private void sendMessage(final String message, final List<String> serviceTokens) {
        try {
            Push.alert(message, keyStorePath, keyStorePassword, false, serviceTokens);
        } catch (CommunicationException e) {
            log.error(e.getMessage());
        } catch (KeystoreException e) {
            log.error(e.getMessage());
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
    }
}
