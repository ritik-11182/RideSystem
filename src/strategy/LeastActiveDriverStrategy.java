package strategy;

import model.Driver;
import model.Rider;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LeastActiveDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        if (drivers == null || drivers.isEmpty()) {
            return null;
        }

        // Simple implementation: finds the least active (available) driver
        // In a real-world scenario, this would track driver activity metrics
        // For now, we'll return a random available driver to simulate "least active"
        List<Driver> availableDrivers = drivers.stream()
                .filter(Driver::isAvailable)
                .collect(Collectors.toList());

        if (availableDrivers.isEmpty()) {
            return null;
        }

        // Return the first available driver (simulating least active)
        return availableDrivers.get(0);
    }
}