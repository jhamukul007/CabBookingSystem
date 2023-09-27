package com.cab.cabbookingsystem.models;

import lombok.Data;

@Data
public class Location extends BaseEntity{
    private Double lat;
    private Double lon;

    public Location(Double lat, Double lon) {
        super();
        this.lat = lat;
        this.lon = lon;
    }

    public Double distance(Location from){
        return Math.sqrt(Math.pow(this.lat-from.lat,2) + Math.pow(this.lon - from.lon, 2));
    }
}
