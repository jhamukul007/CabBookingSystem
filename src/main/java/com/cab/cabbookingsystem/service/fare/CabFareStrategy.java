package com.cab.cabbookingsystem.service.fare;

import com.cab.cabbookingsystem.enums.CabType;
import com.cab.cabbookingsystem.models.Location;

public interface CabFareStrategy {
    Double getFare(Location from, Location to, CabType cabType);
}
