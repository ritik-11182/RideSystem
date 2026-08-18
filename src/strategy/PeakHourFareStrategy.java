package strategy;

import model.Ride;

public class PeakHourFareStrategy implements FareStrategy {
    private static final double BASE_FARE = 50.0;
    private static final double PER_KM_RATE = 10.0;
    private static final double PEAK_HOUR_MULTIPLIER = 1.5;

    @Override
    public double calculateFare(Ride ride) {
        double baseFare = BASE_FARE + (ride.getDistance() * PER_KM_RATE);
        return baseFare * PEAK_HOUR_MULTIPLIER;
    }
}