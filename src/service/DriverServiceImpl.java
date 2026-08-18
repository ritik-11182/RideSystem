package service;

import model.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of IDriverService interface.
 * Manages driver registration, availability, and retrieval operations.
 * Follows Single Responsibility Principle (SRP) - handles only driver-related operations.
 */
public class DriverServiceImpl implements IDriverService {
    private Map<String, Driver> drivers;

    public DriverServiceImpl() {
        this.drivers = new HashMap<>();
    }

    @Override
    public void registerDriver(Driver driver) {
        if (driver == null || driver.getId() == null || driver.getId().isEmpty()) {
            throw new IllegalArgumentException("Invalid driver data");
        }
        if (drivers.containsKey(driver.getId())) {
            throw new IllegalArgumentException("Driver with ID " + driver.getId() + " already exists");
        }
        drivers.put(driver.getId(), driver);
        System.out.println("Driver registered successfully: " + driver);
    }

    @Override
    public void updateAvailability(String driverId, boolean available) {
        if (driverId == null || driverId.isEmpty()) {
            throw new IllegalArgumentException("Driver ID cannot be null or empty");
        }
        Driver driver = drivers.get(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Driver not found with ID: " + driverId);
        }
        driver.setAvailable(available);
        System.out.println("Driver " + driverId + " availability updated to: " + available);
    }

    @Override
    public List<Driver> listAvailableDrivers() {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }

    @Override
    public Driver getDriverById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Driver ID cannot be null or empty");
        }
        return drivers.get(id);
    }

    @Override
    public Map<String, Driver> getAllDrivers() {
        return new HashMap<>(drivers);
    }
}