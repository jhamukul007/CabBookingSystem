package com.cab.cabbookingsystem.models;

import com.cab.cabbookingsystem.enums.TripStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Trip extends BaseEntity{
    private User rider;
    private Cab cab;
    private Location fromLocation;
    private Location toLocation;
    private Double estimateFare;
    private Double totalFare;
    private TripStatus tripStatus;

    public Trip(User rider, Cab cab, Location fromLocation, Location toLocation, Double estimateFare) {
        super();
        this.rider = rider;
        this.cab = cab;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.estimateFare = estimateFare;
        this.tripStatus = TripStatus.WAITING;
    }

}
