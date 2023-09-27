package com.cab.cabbookingsystem.managers.trip;

import com.cab.cabbookingsystem.enums.CabStatus;
import com.cab.cabbookingsystem.enums.TripStatus;
import com.cab.cabbookingsystem.exceptions.TripNotFoundException;
import com.cab.cabbookingsystem.exceptions.TripNotStartedException;
import com.cab.cabbookingsystem.models.Location;
import com.cab.cabbookingsystem.models.Trip;
import com.cab.cabbookingsystem.service.fare.CabFareStrategy;
import com.cab.cabbookingsystem.utils.Loggable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TripManager {
    private final List<Trip> tripList;
    private final CabFareStrategy cabFareStrategy;
    private final Loggable logger;

    public TripManager(CabFareStrategy cabFareStrategy, Loggable logger) {
        this.cabFareStrategy = cabFareStrategy;
        this.logger = logger;
        this.tripList = new ArrayList<>();
    }

    public void addTrip(Trip trip) {
        tripList.add(trip);
    }

    public void endTrip(Location currentLocation, Trip trip) {
        if (!TripStatus.ONGOING.equals(trip.getTripStatus()))
            throw new TripNotStartedException("Trip not started");

        Double totalFare = cabFareStrategy.getFare(trip.getFromLocation(), currentLocation, trip.getCab().getCabType());
        trip.setTotalFare(totalFare);
        trip.setTripStatus(TripStatus.COMPLETED);
        trip.getCab().setCabStatus(CabStatus.AVAILABLE);
        logger.log("Driver ended the trip \n" +
                "Amount " + trip.getTotalFare() + " to be paid");
    }

    public Trip startTrip(String tripId) {
        Trip trip = getTripById(tripId);
        if (trip == null)
            throw new TripNotFoundException("Trip not found on id");
        trip.setTripStatus(TripStatus.ONGOING);
        logger.log("Driver started the trip " + trip.getCab().getCabNumber());
        return trip;
    }

    Trip getTripById(String tripId) {
        return tripList.stream().filter(trip -> Objects.deepEquals(trip.getId(), tripId)).findFirst().orElse(null);
    }

    public List<Trip> tripHistory(String userId) {
        List<Trip> trips = tripList.stream().filter(trip -> Objects.deepEquals(trip.getRider().getId(), userId)).collect(Collectors.toList());
        logger.log("Trip History");
        for (Trip trip : trips) {

            logger.log(String.format("Car Number: %s ", trip.getCab().getCabNumber()) + "\n" +
                    String.format("Driver Name: %s ", trip.getCab().getDriver().getName()) + "\n" +
                    String.format("Total Fare: %s",  trip.getTotalFare()));
        }
        return trips;
    }

    public boolean isRiderAlreadyInOtherRide(String userId) {
        return tripList.stream().filter(trip -> Objects.deepEquals(trip.getRider().getId(), userId)
                && List.of(TripStatus.ONGOING, TripStatus.WAITING).contains(trip.getTripStatus())).findFirst().isPresent();
    }

}
