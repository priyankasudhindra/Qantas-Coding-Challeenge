package com.notificationservice.integration.service;

import com.notificationservice.domain.DeviceDetails;
import com.notificationservice.exception.BadRequestException;

import java.util.List;

public interface DeviceDetailsService {
    public List<DeviceDetails> getDeviceDetails(String bookingId) throws BadRequestException;
}
