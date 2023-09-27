package com.cab.cabbookingsystem.stategy;

import com.cab.cabbookingsystem.enums.CabStatus;
import com.cab.cabbookingsystem.enums.CabType;
import com.cab.cabbookingsystem.exceptions.CabNotAvailableException;
import com.cab.cabbookingsystem.models.Cab;
import com.cab.cabbookingsystem.models.Location;
import com.cab.cabbookingsystem.service.CabSearchStrategy;
import com.cab.cabbookingsystem.utils.DistanceUtil;

import java.util.List;

public class RadiusBasedCabSearchStrategy implements CabSearchStrategy {

    // Default radius 2 km
    private final int DEFAULT_SEARCH_RADIUS = 2;

    @Override
    public Cab searchCab(List<Cab> cabs, Location fromLocation, Location toLocation, CabType cabType) {
        double userLat = fromLocation.getLat();
        double userLon = fromLocation.getLon();

        for (Cab cab : cabs) {
            if (CabStatus.AVAILABLE.equals(cab.getCabStatus())) {
                double cabLat = cab.getCurrentLocation().getLat();
                double cabLon = cab.getCurrentLocation().getLon();

                double distance = DistanceUtil.calculateDistance(cabLat, cabLon, userLat, userLon);
                if (distance <= DEFAULT_SEARCH_RADIUS)
                    return cab;
            }
        }
        throw new CabNotAvailableException("Cab not available with in " + DEFAULT_SEARCH_RADIUS + " kms of radius");
    }
}
