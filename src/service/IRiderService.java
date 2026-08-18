package service;

import model.Rider;
import java.util.Map;

/**
 * Interface for Rider Service operations.
 * Defines the contract for rider management functionality.
 * Follows Interface Segregation Principle (ISP) and Dependency Inversion Principle (DIP).
 */
public interface IRiderService {
    
    /**
     * Registers a new rider in the system.
     * 
     * @param rider The rider to be registered
     * @throws IllegalArgumentException if rider data is invalid or rider already exists
     */
    void registerRider(Rider rider);
    
    /**
     * Retrieves a rider by their unique ID.
     * 
     * @param id The unique identifier of the rider
     * @return The rider object
     * @throws IllegalArgumentException if ID is invalid or rider not found
     */
    Rider getRiderById(String id);
    
    /**
     * Retrieves all registered riders.
     * 
     * @return A map of all riders with their IDs as keys
     */
    Map<String, Rider> getAllRiders();
}