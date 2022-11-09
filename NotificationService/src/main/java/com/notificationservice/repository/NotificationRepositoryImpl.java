package com.notificationservice.repository;

import com.notificationservice.domain.NotificationToken;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.notificationservice.repository.KeyUtil.MATCH_APPKEY;

@Repository(value = "notificationRepository")
public class NotificationRepositoryImpl implements NotificationRepository {
    @Resource(name = "template")
    private StringRedisTemplate template;
    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = template.opsForHash();
    }

    @Override
    public void add(NotificationToken token) {
        final String key = KeyUtil.tokenKey(token.getAppKey(), token.getServiceToken());
        hashOperations.put(key, NotificationToken.USERNAME, token.getUserName());
        hashOperations.put(key, NotificationToken.LONGITUDE, token.getLongitude());
        hashOperations.put(key, NotificationToken.LATITUDE, token.getLatitude());
        template.persist(key);
    }

    public List<String> getAppKeys() {
        List<String> result = new ArrayList<String>();
        for (String k : template.keys(MATCH_APPKEY)) {
            result.add(Key.valueOf(k).getServiceToken());
        }
        return result;
    }

    public List<String> getTokens(final String appKey) {
        List<String> result = new ArrayList<String>();
        for (String k : template.keys(KeyUtil.matchTokenKey(appKey))) {
            result.add(Key.valueOf(k).getServiceToken());
        }
        return result;
    }

    public NotificationToken findBy(final String appKey, final String serviceToken) {
        NotificationToken result = new NotificationToken();
        result.setAppKey(appKey);
        result.setServiceToken(serviceToken);
        final String key = KeyUtil.tokenKey(appKey, serviceToken);
        result.setUserName(get(key, NotificationToken.USERNAME));
        result.setLatitude(get(key, NotificationToken.LATITUDE));
        result.setLongitude(get(key, NotificationToken.LONGITUDE));
        return result;
    }

    public List<NotificationToken> findBy(final String appKey) {
        List<NotificationToken> result = new ArrayList<NotificationToken>();
        for (String token : getTokens(appKey)) {
            result.add(findBy(appKey, token));
        }
        return result;
    }

    @Override
    public List<NotificationToken> findAll() {
        List<NotificationToken> result = new ArrayList<NotificationToken>();
        for (String k : template.keys(MATCH_APPKEY)) {
            Key key = Key.valueOf(k);
            result.add(findBy(key.getAppKey(), key.getServiceToken()));
        }
        return result;
    }

    private String get(final String key, final String hashKey) {
        return hashOperations.get(key, hashKey);
    }

    public static final class Key {
        private final String appKey;
        private final String serviceToken;

        public Key(String appKey, String serviceToken) {
            this.appKey = appKey;
            this.serviceToken = serviceToken;
        }

        public String getAppKey() {
            return appKey;
        }

        public String getServiceToken() {
            return serviceToken;
        }

        public static Key valueOf(final String k) {
            String[] parts = k.split(":");
            return new Key(parts[1], parts[3]);
        }
    }
}
