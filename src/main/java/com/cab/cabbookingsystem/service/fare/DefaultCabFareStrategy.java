package com.cab.cabbookingsystem.service.fare;

import com.cab.cabbookingsystem.enums.CabType;
import com.cab.cabbookingsystem.models.Location;
import com.cab.cabbookingsystem.utils.DistanceUtil;

public class DefaultCabFareStrategy implements CabFareStrategy {
    @Override
    public Double getFare(Location from, Location to, CabType cabType) {
        Double distance = DistanceUtil.calculateDistance(from.getLat(), from.getLon(), to.getLat(), to.getLon());
        return distance * cabType.getPerKmFare();
    }


}
