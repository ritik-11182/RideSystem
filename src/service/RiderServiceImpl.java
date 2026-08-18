package service;

import model.Rider;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of IRiderService interface.
 * Manages rider registration and retrieval operations.
 * Follows Single Responsibility Principle (SRP) - handles only rider-related operations.
 */
public class RiderServiceImpl implements IRiderService {
    private Map<String, Rider> riders;

    public RiderServiceImpl() {
        this.riders = new HashMap<>();
    }

    @Override
    public void registerRider(Rider rider) {
        if (rider == null || rider.getId() == null || rider.getId().isEmpty()) {
            throw new IllegalArgumentException("Invalid rider data");
        }
        if (riders.containsKey(rider.getId())) {
            throw new IllegalArgumentException("Rider with ID " + rider.getId() + " already exists");
        }
        riders.put(rider.getId(), rider);
        System.out.println("Rider registered successfully: " + rider);
    }

    @Override
    public Rider getRiderById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Rider ID cannot be null or empty");
        }
        Rider rider = riders.get(id);
        if (rider == null) {
            throw new IllegalArgumentException("Rider not found with ID: " + id);
        }
        return rider;
    }

    @Override
    public Map<String, Rider> getAllRiders() {
        return new HashMap<>(riders);
    }
}