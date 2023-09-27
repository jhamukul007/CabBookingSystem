package com.cab.cabbookingsystem.models;

import com.cab.cabbookingsystem.enums.CabStatus;
import com.cab.cabbookingsystem.enums.CabType;
import lombok.Data;
import lombok.ToString;

import java.sql.Driver;

@Data
@ToString(callSuper = true)
public class Cab extends BaseEntity{
    private String cabNumber;
    private User driver;
    //private Trip currentTrip;
    private Location currentLocation;
    private CabStatus cabStatus;
    private CabType cabType;

    public Cab(String cabNumber, User driver, CabType cabType) {
        super();
        this.cabNumber = cabNumber;
        this.driver = driver;
        this.cabType = cabType;
        this.cabStatus = CabStatus.AVAILABLE;
    }
}
