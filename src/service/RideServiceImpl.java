package service;

import model.*;
import strategy.FareStrategy;
import strategy.RideMatchingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of IRideService interface.
 * Orchestrates ride lifecycle operations including request, assignment, completion, and cancellation.
 * Follows Single Responsibility Principle (SRP) - handles only ride-related operations.
 * Demonstrates Dependency Inversion Principle (DIP) - depends on abstractions (strategies) not concretions.
 */
public class RideServiceImpl implements IRideService {
    private Map<String, Ride> rides;
    private RideMatchingStrategy rideMatchingStrategy;
    private FareStrategy fareStrategy;
    private IDriverService driverService;
    private int rideCounter;

    public RideServiceImpl(RideMatchingStrategy rideMatchingStrategy,
                           FareStrategy fareStrategy,
                           IDriverService driverService) {
        this.rides = new HashMap<>();
        this.rideMatchingStrategy = rideMatchingStrategy;
        this.fareStrategy = fareStrategy;
        this.driverService = driverService;
        this.rideCounter = 1;
    }

    @Override
    public Ride requestRide(Rider rider, double distance) {
        if (rider == null) {
            throw new IllegalArgumentException("Rider cannot be null");
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be greater than 0");
        }

        String rideId = "RIDE" + String.format("%03d", rideCounter++);
        Ride ride = new Ride(rideId, rider, distance);
        rides.put(rideId, ride);
        
        System.out.println("Ride requested: " + ride);
        return ride;
    }

    @Override
    public void assignDriver(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found with ID: " + rideId);
        }

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride is not in REQUESTED status");
        }

        List<Driver> availableDrivers = driverService.listAvailableDrivers();
        if (availableDrivers.isEmpty()) {
            throw new IllegalStateException("No available drivers found");
        }

        Driver assignedDriver = rideMatchingStrategy.findDriver(ride.getRider(), availableDrivers);
        if (assignedDriver == null) {
            throw new IllegalStateException("Could not find a suitable driver");
        }

        ride.setDriver(assignedDriver);
        ride.setStatus(RideStatus.ASSIGNED);
        driverService.updateAvailability(assignedDriver.getId(), false);
        
        System.out.println("Driver assigned to ride: " + assignedDriver.getName() + " for Ride ID: " + rideId);
    }

    @Override
    public FareReceipt completeRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found with ID: " + rideId);
        }

        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new IllegalStateException("Ride is not in ASSIGNED status");
        }

        ride.setStatus(RideStatus.COMPLETED);
        
        // Make driver available again
        if (ride.getDriver() != null) {
            driverService.updateAvailability(ride.getDriver().getId(), true);
        }

        double fare = fareStrategy.calculateFare(ride);
        FareReceipt receipt = new FareReceipt(rideId, fare);
        
        System.out.println("Ride completed: " + rideId);
        System.out.println("Fare Receipt: " + receipt);
        
        return receipt;
    }

    @Override
    public void cancelRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found with ID: " + rideId);
        }

        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed ride");
        }

        ride.setStatus(RideStatus.CANCELLED);
        
        // Make driver available again if assigned
        if (ride.getDriver() != null) {
            driverService.updateAvailability(ride.getDriver().getId(), true);
        }
        
        System.out.println("Ride cancelled: " + rideId);
    }

    @Override
    public Ride getRideById(String rideId) {
        return rides.get(rideId);
    }

    @Override
    public List<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }

    @Override
    public void setRideMatchingStrategy(RideMatchingStrategy strategy) {
        this.rideMatchingStrategy = strategy;
    }

    @Override
    public void setFareStrategy(FareStrategy strategy) {
        this.fareStrategy = strategy;
    }
}