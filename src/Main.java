import model.*;
import service.*;
import strategy.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static IRiderService riderService;
    private static IDriverService driverService;
    private static IRideService rideService;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeServices();
        scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("   Welcome to Ride System Application  ");
        System.out.println("========================================\n");

        boolean running = true;
        while (running) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                running = handleMenuChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Invalid input! Please enter a number.\n");
            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage() + "\n");
            }
        }

        scanner.close();
        System.out.println("\nThank you for using Ride System. Goodbye!");
    }

    private static void initializeServices() {
        riderService = new RiderServiceImpl();
        driverService = new DriverServiceImpl();

        // Initialize with default strategies
        RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();
        FareStrategy fareStrategy = new DefaultFareStrategy();
        rideService = new RideServiceImpl(matchingStrategy, fareStrategy, driverService);
    }

    private static void displayMenu() {
        System.out.println("========================================");
        System.out.println("              MAIN MENU                 ");
        System.out.println("========================================");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. View Rides");
        System.out.println("7. Cancel Ride");
        System.out.println("8. Change Ride Matching Strategy");
        System.out.println("9. Change Fare Strategy");
        System.out.println("0. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }

    private static boolean handleMenuChoice(int choice) {
        System.out.println();
        switch (choice) {
            case 1:
                addRider();
                break;
            case 2:
                addDriver();
                break;
            case 3:
                viewAvailableDrivers();
                break;
            case 4:
                requestRide();
                break;
            case 5:
                completeRide();
                break;
            case 6:
                viewRides();
                break;
            case 7:
                cancelRide();
                break;
            case 8:
                changeRideMatchingStrategy();
                break;
            case 9:
                changeFareStrategy();
                break;
            case 0:
                return false;
            default:
                System.out.println("❌ Invalid choice! Please select a valid option.\n");
        }
        return true;
    }

    private static void addRider() {
        try {
            System.out.println("--- Add New Rider ---");
            System.out.print("Enter Rider ID: ");
            String id = scanner.nextLine().trim();

            System.out.print("Enter Rider Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Rider Location: ");
            String location = scanner.nextLine().trim();

            if (id.isEmpty() || name.isEmpty() || location.isEmpty()) {
                System.out.println("❌ All fields are required!\n");
                return;
            }

            Rider rider = new Rider(id, name, location);
            riderService.registerRider(rider);
            System.out.println("✅ Rider added successfully!\n");
        } catch (Exception e) {
            System.out.println("❌ Error adding rider: " + e.getMessage() + "\n");
        }
    }

    private static void addDriver() {
        try {
            System.out.println("--- Add New Driver ---");
            System.out.print("Enter Driver ID: ");
            String id = scanner.nextLine().trim();

            System.out.print("Enter Driver Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Driver Location: ");
            String location = scanner.nextLine().trim();

            System.out.println("Select Vehicle Type:");
            System.out.println("1. BIKE");
            System.out.println("2. AUTO");
            System.out.println("3. CAR");
            System.out.print("Enter choice: ");
            int vehicleChoice = Integer.parseInt(scanner.nextLine());

            if (id.isEmpty() || name.isEmpty() || location.isEmpty()) {
                System.out.println("❌ All fields are required!\n");
                return;
            }

            VehicleType vehicleType;
            switch (vehicleChoice) {
                case 1:
                    vehicleType = VehicleType.BIKE;
                    break;
                case 2:
                    vehicleType = VehicleType.AUTO;
                    break;
                case 3:
                    vehicleType = VehicleType.CAR;
                    break;
                default:
                    System.out.println("❌ Invalid vehicle type! Defaulting to BIKE.\n");
                    vehicleType = VehicleType.BIKE;
            }

            Driver driver = new Driver(id, name, location, vehicleType);
            driverService.registerDriver(driver);
            System.out.println("✅ Driver added successfully!\n");
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input for vehicle type!\n");
        } catch (Exception e) {
            System.out.println("❌ Error adding driver: " + e.getMessage() + "\n");
        }
    }

    private static void viewAvailableDrivers() {
        System.out.println("--- Available Drivers ---");
        List<Driver> availableDrivers = driverService.listAvailableDrivers();

        if (availableDrivers.isEmpty()) {
            System.out.println("No available drivers at the moment.\n");
        } else {
            System.out.println("Total Available Drivers: " + availableDrivers.size());
            for (Driver driver : availableDrivers) {
                System.out.println(driver);
            }
            System.out.println();
        }
    }

    private static void requestRide() {
        try {
            System.out.println("--- Request a Ride ---");
            System.out.print("Enter Rider ID: ");
            String riderId = scanner.nextLine().trim();

            System.out.print("Enter Distance (in km): ");
            double distance = Double.parseDouble(scanner.nextLine());

            Rider rider = riderService.getRiderById(riderId);
            Ride ride = rideService.requestRide(rider, distance);

            // Automatically assign driver
            rideService.assignDriver(ride.getId());

            System.out.println("✅ Ride requested and driver assigned successfully!");
            System.out.println("Ride Details: " + ride);
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid distance value!\n");
        } catch (Exception e) {
            System.out.println("❌ Error requesting ride: " + e.getMessage() + "\n");
        }
    }

    private static void completeRide() {
        try {
            System.out.println("--- Complete a Ride ---");
            System.out.print("Enter Ride ID: ");
            String rideId = scanner.nextLine().trim();

            FareReceipt receipt = rideService.completeRide(rideId);
            System.out.println("✅ Ride completed successfully!");
            System.out.println("Receipt: " + receipt);
            System.out.println();
        } catch (Exception e) {
            System.out.println("❌ Error completing ride: " + e.getMessage() + "\n");
        }
    }

    private static void viewRides() {
        System.out.println("--- All Rides ---");
        List<Ride> rides = rideService.getAllRides();

        if (rides.isEmpty()) {
            System.out.println("No rides found.\n");
        } else {
            System.out.println("Total Rides: " + rides.size());
            for (Ride ride : rides) {
                System.out.println(ride);
            }
            System.out.println();
        }
    }

    private static void cancelRide() {
        try {
            System.out.println("--- Cancel a Ride ---");
            System.out.print("Enter Ride ID: ");
            String rideId = scanner.nextLine().trim();

            rideService.cancelRide(rideId);
            System.out.println("✅ Ride cancelled successfully!\n");
        } catch (Exception e) {
            System.out.println("❌ Error cancelling ride: " + e.getMessage() + "\n");
        }
    }

    private static void changeRideMatchingStrategy() {
        System.out.println("--- Change Ride Matching Strategy ---");
        System.out.println("1. Nearest Driver Strategy");
        System.out.println("2. Least Active Driver Strategy");
        System.out.print("Enter choice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            RideMatchingStrategy strategy;

            switch (choice) {
                case 1:
                    strategy = new NearestDriverStrategy();
                    System.out.println("✅ Switched to Nearest Driver Strategy\n");
                    break;
                case 2:
                    strategy = new LeastActiveDriverStrategy();
                    System.out.println("✅ Switched to Least Active Driver Strategy\n");
                    break;
                default:
                    System.out.println("❌ Invalid choice!\n");
                    return;
            }

            rideService.setRideMatchingStrategy(strategy);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input!\n");
        }
    }

    private static void changeFareStrategy() {
        System.out.println("--- Change Fare Strategy ---");
        System.out.println("1. Default Fare Strategy");
        System.out.println("2. Peak Hour Fare Strategy");
        System.out.print("Enter choice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            FareStrategy strategy;

            switch (choice) {
                case 1:
                    strategy = new DefaultFareStrategy();
                    System.out.println("✅ Switched to Default Fare Strategy\n");
                    break;
                case 2:
                    strategy = new PeakHourFareStrategy();
                    System.out.println("✅ Switched to Peak Hour Fare Strategy\n");
                    break;
                default:
                    System.out.println("❌ Invalid choice!\n");
                    return;
            }

            rideService.setFareStrategy(strategy);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input!\n");
        }
    }
}