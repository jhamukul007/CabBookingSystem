package com.cab.cabbookingsystem.managers.cab;

import com.cab.cabbookingsystem.enums.CabStatus;
import com.cab.cabbookingsystem.enums.CabType;
import com.cab.cabbookingsystem.exceptions.CabAlreadyExistException;
import com.cab.cabbookingsystem.exceptions.CabNotFoundException;
import com.cab.cabbookingsystem.exceptions.RiderAlreadyInOtherRideException;
import com.cab.cabbookingsystem.exceptions.UserNotFoundException;
import com.cab.cabbookingsystem.managers.trip.TripManager;
import com.cab.cabbookingsystem.managers.user.UserManager;
import com.cab.cabbookingsystem.models.Cab;
import com.cab.cabbookingsystem.models.Location;
import com.cab.cabbookingsystem.models.Trip;
import com.cab.cabbookingsystem.models.User;
import com.cab.cabbookingsystem.service.CabSearchStrategy;
import com.cab.cabbookingsystem.service.fare.CabFareStrategy;
import com.cab.cabbookingsystem.utils.Loggable;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class CabManager {
    private final List<Cab> cabList;
    private final UserManager userManager;
    private final CabSearchStrategy cabSearchStrategy;

    private final CabFareStrategy cabFareStrategy;

    private final TripManager tripManager;
    private final Loggable logger;
    public CabManager(UserManager userManager, CabSearchStrategy cabSearchStrategy, CabFareStrategy cabFareStrategy, TripManager tripManager, Loggable logger) {
        this.userManager = userManager;
        this.cabSearchStrategy = cabSearchStrategy;
        this.cabFareStrategy = cabFareStrategy;
        this.tripManager = tripManager;
        this.logger = logger;
        this.cabList = new ArrayList<>();
    }

    public Cab registerCab(@NonNull User driver, @NonNull String cabNumber, @NonNull CabType cabType) {
        Cab existedCab = getByCabNumber(cabNumber);
        if (existedCab != null)
            throw new CabAlreadyExistException("Cab with number already exist");
        Cab cab = new Cab(cabNumber, driver, cabType);
        cabList.add(cab);
        //logger.log("Micro Cab Added:  " + microCab1);
        logger.log(String.format("Cab with type %s and number %s has been add successfully ", cabType,
                cabNumber) + "\n " +
                " Details " + cab);
        return cab;
    }

    public Cab getByCabNumber(@NonNull String cabNumber) {
        return cabList.stream().filter(cab -> Objects.deepEquals(cabNumber, cab.getCabNumber())).findFirst().orElse(null);
    }

    public Cab updateLocation(@NonNull String cabNumber, @NonNull Location currentLocation) {
        Cab cab = getByCabNumber(cabNumber);
        if (cab == null)
            throw new CabNotFoundException("Cab not exist on number");
        cab.setCurrentLocation(currentLocation);
        return cab;
    }

    public Trip searchAndBookCab(@NonNull User rider, @NonNull Location from, @NonNull Location to, @NonNull CabType cabType) {
        User user = userManager.getByPhone(rider.getPhone());
        if (user == null)
            throw new UserNotFoundException("User not found on mobile number ");
        if(tripManager.isRiderAlreadyInOtherRide(user.getId()))
            throw new RiderAlreadyInOtherRideException("User already in other ride");

        Cab cab = cabSearchStrategy.searchCab(cabList, from, to, cabType);
        cab.setCabStatus(CabStatus.NOT_AVAILABLE);
        Double estimatedFare = cabFareStrategy.getFare(from, to, cabType);
        Trip trip = new Trip(rider, cab, from, to, estimatedFare);
        tripManager.addTrip(trip);
        logger.log("Cab Arriving Number: " + trip.getCab().getCabNumber() + "\n" +
                "Cab Driver Number: " + trip.getCab().getDriver().getPhone() + "\n" +
                "Estimated Fare : "+ trip.getEstimateFare());
        return trip;
    }
}
