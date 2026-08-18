package service;

import model.FareReceipt;
import model.Ride;
import model.Rider;
import strategy.FareStrategy;
import strategy.RideMatchingStrategy;

import java.util.List;

/**
 * Interface for Ride Service operations.
 * Defines the contract for ride management and lifecycle operations.
 * Follows Interface Segregation Principle (ISP) and Dependency Inversion Principle (DIP).
 */
public interface IRideService {
    
    /**
     * Creates a new ride request.
     * 
     * @param rider The rider requesting the ride
     * @param distance The distance of the ride in kilometers
     * @return The created ride object
     * @throws IllegalArgumentException if rider is null or distance is invalid
     */
    Ride requestRide(Rider rider, double distance);
    
    /**
     * Assigns a driver to a requested ride using the configured matching strategy.
     * 
     * @param rideId The unique identifier of the ride
     * @throws IllegalArgumentException if ride is not found
     * @throws IllegalStateException if ride is not in REQUESTED status or no drivers available
     */
    void assignDriver(String rideId);
    
    /**
     * Completes a ride and generates a fare receipt.
     * 
     * @param rideId The unique identifier of the ride
     * @return The fare receipt for the completed ride
     * @throws IllegalArgumentException if ride is not found
     * @throws IllegalStateException if ride is not in ASSIGNED status
     */
    FareReceipt completeRide(String rideId);
    
    /**
     * Cancels a ride.
     * 
     * @param rideId The unique identifier of the ride
     * @throws IllegalArgumentException if ride is not found
     * @throws IllegalStateException if ride is already completed
     */
    void cancelRide(String rideId);
    
    /**
     * Retrieves a ride by its unique ID.
     * 
     * @param rideId The unique identifier of the ride
     * @return The ride object, or null if not found
     */
    Ride getRideById(String rideId);
    
    /**
     * Retrieves all rides in the system.
     * 
     * @return A list of all rides
     */
    List<Ride> getAllRides();
    
    /**
     * Sets the ride matching strategy for driver assignment.
     * 
     * @param strategy The ride matching strategy to use
     */
    void setRideMatchingStrategy(RideMatchingStrategy strategy);
    
    /**
     * Sets the fare calculation strategy.
     * 
     * @param strategy The fare strategy to use
     */
    void setFareStrategy(FareStrategy strategy);
}