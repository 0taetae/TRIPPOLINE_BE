package com.ssafy.trip.spot.dto.request;

import lombok.Data;

@Data
public class NearbySearchRequest {
    private double minLatitude;
    private double maxLatitude;
    private double minLongitude;
    private double maxLongitude;
    private Long cursor; // 마지막 ID
    private int limit;
}