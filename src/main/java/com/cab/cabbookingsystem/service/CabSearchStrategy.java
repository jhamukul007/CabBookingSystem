package com.cab.cabbookingsystem.service;

import com.cab.cabbookingsystem.enums.CabType;
import com.cab.cabbookingsystem.models.Cab;
import com.cab.cabbookingsystem.models.Location;
import com.cab.cabbookingsystem.models.User;

import java.util.List;

public interface CabSearchStrategy{
    Cab searchCab(List<Cab> cabs, Location fromLocation, Location toLocation, CabType cabType);
}
