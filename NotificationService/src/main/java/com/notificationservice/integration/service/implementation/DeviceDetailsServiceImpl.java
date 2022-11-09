package com.notificationservice.integration.service.implementation;

import com.notificationservice.domain.DeviceDetails;
import com.notificationservice.integration.service.DeviceDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DeviceDetailsServiceImpl implements DeviceDetailsService {

    @Value(value = "${deviceDetailsUrl}")
    private String deviceDetailsUrl;

    private final RestTemplate restTemplate;

    public DeviceDetailsServiceImpl(RestTemplate restTemplate) {
        super();
        this.restTemplate = restTemplate;
    }

    @Override
    public List<DeviceDetails> getDeviceDetails(String bookingId) {
        ResponseEntity<List<DeviceDetails>> entity = null;
        List<DeviceDetails> deviceDetails;
        try {
            entity = restTemplate.exchange(deviceDetailsUrl+bookingId+"/devices", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
            if (!HttpStatus.OK.equals(entity.getStatusCode())) {
                log.error("No good response. StatusCode={}", entity.getStatusCode());
                return getDefaultResponse();
            }

            deviceDetails = entity.getBody();
            return deviceDetails;
        } catch (HttpClientErrorException exception) {
            log.error("Exception occurred while retrieving device details");

            int statusCode = exception.getRawStatusCode();
            if (statusCode == HttpStatus.UNAUTHORIZED.value() || statusCode == HttpStatus.FORBIDDEN.value()) {
                log.error("unauthorized/forbidden: " + statusCode);
            }
            exception.printStackTrace();
        }

        if (entity == null) {
            log.error("No good response.");
        }
        return getDefaultResponse();
    }

    private List<DeviceDetails> getDefaultResponse() {
        return Collections.emptyList();
    }
}
