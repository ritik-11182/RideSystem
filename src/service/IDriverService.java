package service;

import model.Driver;
import java.util.List;
import java.util.Map;

/**
 * Interface for Driver Service operations.
 * Defines the contract for driver management functionality.
 * Follows Interface Segregation Principle (ISP) and Dependency Inversion Principle (DIP).
 */
public interface IDriverService {
    
    /**
     * Registers a new driver in the system.
     * 
     * @param driver The driver to be registered
     * @throws IllegalArgumentException if driver data is invalid or driver already exists
     */
    void registerDriver(Driver driver);
    
    /**
     * Updates the availability status of a driver.
     * 
     * @param driverId The unique identifier of the driver
     * @param available The new availability status
     * @throws IllegalArgumentException if driver ID is invalid or driver not found
     */
    void updateAvailability(String driverId, boolean available);
    
    /**
     * Retrieves all available drivers.
     * 
     * @return A list of available drivers
     */
    List<Driver> listAvailableDrivers();
    
    /**
     * Retrieves a driver by their unique ID.
     * 
     * @param id The unique identifier of the driver
     * @return The driver object, or null if not found
     */
    Driver getDriverById(String id);
    
    /**
     * Retrieves all registered drivers.
     * 
     * @return A map of all drivers with their IDs as keys
     */
    Map<String, Driver> getAllDrivers();
}