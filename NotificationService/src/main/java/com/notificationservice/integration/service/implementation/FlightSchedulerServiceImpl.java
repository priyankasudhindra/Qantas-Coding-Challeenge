package com.notificationservice.integration.service.implementation;

import com.notificationservice.domain.FlightScheduler;
import com.notificationservice.integration.service.FlightSchedulerService;
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
public class FlightSchedulerServiceImpl implements FlightSchedulerService {

    @Value(value = "${flightSchedulerUrl}")
    private String flightSchedulerUrl;

    private final RestTemplate restTemplate;

    public FlightSchedulerServiceImpl(RestTemplate restTemplate) {
        super();
        this.restTemplate = restTemplate;
    }

    @Override
    public List<FlightScheduler> getFlightScheduler() {
        ResponseEntity<List<FlightScheduler>> entity = null;
        List<FlightScheduler> flightScheduler;
        try {
            entity = restTemplate.exchange(flightSchedulerUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
            if (!HttpStatus.OK.equals(entity.getStatusCode())) {
                log.error("No good response. StatusCode={}", entity.getStatusCode());
                return getDefaultResponse();
            }

            flightScheduler = entity.getBody();
            return flightScheduler;
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

    private List<FlightScheduler> getDefaultResponse() {
        return Collections.emptyList();
    }
}
