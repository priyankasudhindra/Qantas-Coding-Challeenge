package com.notificationservice.integration.service;

import com.notificationservice.domain.DeviceDetails;
import com.notificationservice.domain.FlightScheduler;

import java.util.List;

public interface FlightSchedulerService {
    public List<FlightScheduler> getFlightScheduler();
}
