package com.cab.cabbookingsystem;

import com.cab.cabbookingsystem.enums.CabType;
import com.cab.cabbookingsystem.enums.UserType;
import com.cab.cabbookingsystem.managers.cab.CabManager;
import com.cab.cabbookingsystem.managers.trip.TripManager;
import com.cab.cabbookingsystem.managers.user.UserManager;
import com.cab.cabbookingsystem.managers.user.UserManagerImpl;
import com.cab.cabbookingsystem.models.Cab;
import com.cab.cabbookingsystem.models.Location;
import com.cab.cabbookingsystem.models.Trip;
import com.cab.cabbookingsystem.models.User;
import com.cab.cabbookingsystem.service.CabSearchStrategy;
import com.cab.cabbookingsystem.service.fare.CabFareStrategy;
import com.cab.cabbookingsystem.service.fare.DefaultCabFareStrategy;
import com.cab.cabbookingsystem.stategy.DefaultCabSearchStrategy;
import com.cab.cabbookingsystem.stategy.RadiusBasedCabSearchStrategy;
import com.cab.cabbookingsystem.utils.ConsoleLogger;
import com.cab.cabbookingsystem.utils.Loggable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CabBookingSystemApplicationTests {

    private Loggable logger;
    private UserManager userManager;
    private CabSearchStrategy cabSearchStrategy;
    private CabFareStrategy cabFareStrategy;
    private  TripManager tripManager;
    private CabManager cabManager;

    @BeforeAll
    public void init(){
        logger = new ConsoleLogger();
        userManager = new UserManagerImpl(logger);

        //cabSearchStrategy = new DefaultCabSearchStrategy();
        cabSearchStrategy = new RadiusBasedCabSearchStrategy();
        cabFareStrategy = new DefaultCabFareStrategy();
        tripManager = new TripManager(cabFareStrategy, logger);
        cabManager = new CabManager(userManager, cabSearchStrategy, cabFareStrategy, tripManager, logger);
    }

    @Test
    public void testSuccessBookingFlow(){
        // Rider and driver registration
        User rider1 = userManager.registerUser("Mukul", "8769133427", UserType.RIDER);
        User rider2 = userManager.registerUser("Jack", "9699011399", UserType.RIDER);
        User rider3 = userManager.registerUser("Rahul", "9931890299", UserType.RIDER);
        User driver1 = userManager.registerUser("Raj", "9931890233", UserType.DRIVER);
        User driver2 = userManager.registerUser("Mohan", "9931890243", UserType.DRIVER);
        User driver3 = userManager.registerUser("Kishan", "9931890343", UserType.DRIVER);
        User driver4 = userManager.registerUser("Raju", "9931890340", UserType.DRIVER);
        User driver5 = userManager.registerUser("Jha", "9931890341", UserType.DRIVER);

        // Cab registration
        Cab microCab1 = cabManager.registerCab(driver1, "KA03JS9090", CabType.MICRO);
        Cab microCab2 = cabManager.registerCab(driver2, "KA03JS9091", CabType.MICRO);
        Cab microCab3 = cabManager.registerCab(driver3, "KA03JS9093", CabType.MICRO);
        Cab minCab1 = cabManager.registerCab(driver4, "KA03JS9094", CabType.MINI);
        Cab minCab2 = cabManager.registerCab(driver5, "KA03JS9095", CabType.MINI);

        // Update cab location
        cabManager.updateLocation(minCab1.getCabNumber(), new Location(4D, 5D));
        cabManager.updateLocation(minCab2.getCabNumber(), new Location(12D, 15D));
        cabManager.updateLocation(microCab1.getCabNumber(), new Location(3D, 15D));
        cabManager.updateLocation(microCab2.getCabNumber(), new Location(20D, 30D));

        // Search and book the cab
//        Trip trip = cabManager.searchAndBookCab(rider1, new Location(4D, 5D), new Location(30D, 20D), CabType.MINI);
//        tripManager.startTrip(trip.getId());
//        tripManager.endTrip(new Location(30D, 30D), trip);

//        Trip trip2 = cabManager.searchAndBookCab(rider2, new Location(4D, 5D), new Location(30D, 20D), CabType.MINI);
//        tripManager.startTrip(trip2.getId());
//        tripManager.endTrip(new Location(30D, 20D), trip2);
//        tripManager.tripHistory(rider2.getId());

        //cabSearchStrategy = new RadiusBasedCabSearchStrategy();
       // cabManager = new CabManager(userManager, cabSearchStrategy, cabFareStrategy, tripManager, logger);

        cabManager.updateLocation(microCab2.getCabNumber(), new Location(12.912452D,77.676564D));

        Trip trip3 = cabManager.searchAndBookCab(rider3, new Location(12.913372,77.677482), new Location(12.913189,77.636657), CabType.MICRO);
        tripManager.startTrip(trip3.getId());
        tripManager.endTrip(new Location(12.913189,77.636657), trip3);
        tripManager.tripHistory(rider3.getId());

        cabManager.updateLocation(minCab1.getCabNumber(), new Location(12.912094,77.609825));
        cabManager.updateLocation(microCab3.getCabNumber(), new Location(12.957998,77.684318));
        Trip trip4 = cabManager.searchAndBookCab(rider2, new Location(12.915987,77.613966), new Location(12.956492,77.701066), CabType.MINI);
        tripManager.startTrip(trip4.getId());
        tripManager.endTrip(new Location(12.956492,77.701066), trip4);

        tripManager.tripHistory(rider2.getId());

    }

}
