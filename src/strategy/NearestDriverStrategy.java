package strategy;

import model.Driver;
import model.Rider;
import java.util.List;

public class NearestDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        if (drivers == null || drivers.isEmpty()) {
            return null;
        }

        // Simple implementation: returns the first available driver
        // In a real-world scenario, this would calculate actual distance
        // based on rider.getLocation() and driver.getCurrentLocation()
        for (Driver driver : drivers) {
            if (driver.isAvailable()) {
                return driver;
            }
        }
        return null;
    }
}