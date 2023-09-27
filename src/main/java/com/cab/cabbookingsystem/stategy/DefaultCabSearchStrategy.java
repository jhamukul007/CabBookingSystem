package com.cab.cabbookingsystem.stategy;

import com.cab.cabbookingsystem.enums.CabStatus;
import com.cab.cabbookingsystem.enums.CabType;
import com.cab.cabbookingsystem.models.Cab;
import com.cab.cabbookingsystem.exceptions.CabNotAvailableException;
import com.cab.cabbookingsystem.models.Location;
import com.cab.cabbookingsystem.service.CabSearchStrategy;

import java.util.List;
import java.util.Objects;

public class DefaultCabSearchStrategy implements CabSearchStrategy {


    public DefaultCabSearchStrategy(){}

    @Override
    public Cab searchCab(List<Cab> cabs, Location fromLocation, Location toLocation, CabType cabType) {
        return cabs.stream().filter(cab -> CabStatus.AVAILABLE.equals(cab.getCabStatus()) && Objects.deepEquals(cabType, cab.getCabType()))
                .findFirst().orElseThrow(() -> new CabNotAvailableException("Can not available"));
    }
}
