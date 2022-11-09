package com.notificationservice.domain;

import java.math.BigDecimal;

public class Location {
    private final BigDecimal longitude;
    private final BigDecimal latitude;

    public Location(BigDecimal longitude, BigDecimal latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public static Location valueOf(String longitude, String latitude) {
        BigDecimal lo = new BigDecimal(latitude);
        BigDecimal la = new BigDecimal(latitude);
        return new Location(lo, la);
    }
}
