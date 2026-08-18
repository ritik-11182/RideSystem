# Ride System - Java Console Application

## Overview
A comprehensive ride-sharing system built with Java that demonstrates SOLID principles, design patterns, and clean architecture.

## Features
- **Rider Management**: Register and manage riders
- **Driver Management**: Register drivers with vehicle types (BIKE, AUTO, CAR)
- **Ride Booking**: Request rides with automatic driver assignment
- **Fare Calculation**: Multiple pricing strategies (Default and Peak Hour)
- **Driver Matching**: Pluggable strategies (Nearest Driver, Least Active Driver)
- **Ride Tracking**: Monitor ride status (REQUESTED, ASSIGNED, COMPLETED, CANCELLED)

## Architecture

### Package Structure
```
src/
├── model/              # Domain entities
│   ├── Rider.java
│   ├── Driver.java
│   ├── Ride.java
│   ├── FareReceipt.java
│   ├── RideStatus.java (enum)
│   └── VehicleType.java (enum)
├── strategy/           # Strategy pattern implementations
│   ├── RideMatchingStrategy.java (interface)
│   ├── NearestDriverStrategy.java
│   ├── LeastActiveDriverStrategy.java
│   ├── FareStrategy.java (interface)
│   ├── DefaultFareStrategy.java
│   └── PeakHourFareStrategy.java
├── service/            # Business logic layer
│   ├── RiderService.java
│   ├── DriverService.java
│   └── RideService.java
└── Main.java           # Console application entry point
```

## SOLID Principles Implementation

### 1. Single Responsibility Principle (SRP)
- **RiderService**: Handles only rider-related operations
- **DriverService**: Manages driver registration and availability
- **RideService**: Orchestrates ride lifecycle
- **FareCalculator**: Separated into strategy implementations

### 2. Open/Closed Principle (OCP)
- New ride matching strategies can be added without modifying RideService
- New fare calculation strategies can be added without changing core logic
- Extensible through strategy interfaces

### 3. Liskov Substitution Principle (LSP)
- Any `RideMatchingStrategy` implementation can replace another
- Any `FareStrategy` implementation can replace another
- Strategies are fully interchangeable at runtime

### 4. Interface Segregation Principle (ISP)
- Small, focused interfaces: `RideMatchingStrategy`, `FareStrategy`
- Clients depend only on methods they use

### 5. Dependency Inversion Principle (DIP)
- RideService depends on `RideMatchingStrategy` interface, not concrete implementations
- RideService depends on `FareStrategy` interface, not concrete implementations
- High-level modules don't depend on low-level modules

## Design Patterns

### Strategy Pattern
**Ride Matching Strategies:**
- `NearestDriverStrategy`: Finds the nearest available driver
- `LeastActiveDriverStrategy`: Selects the least active driver

**Fare Calculation Strategies:**
- `DefaultFareStrategy`: Base fare + per km rate
- `PeakHourFareStrategy`: 1.5x multiplier on default fare

### Composition over Inheritance
- Strategies are i\njected into RideService constructor
- Runtime strategy switching supported

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- IntelliJ IDEA or any Java IDE

### Running the Application
1. Open the project in IntelliJ IDEA
2. Navigate to `src/Main.java`
3. Run the main method
4. Follow the console menu prompts

### Menu Options
```
1. Add Rider
2. Add Driver
3. View Available Drivers
4. Request Ride
5. Complete Ride
6. View Rides
7. Cancel Ride
8. Change Ride Matching Strategy
9. Change Fare Strategy
0. Exit
```

## Usage Examples

### Adding a Rider
```
Enter Rider ID: R001
Enter Rider Name: John Doe
Enter Rider Location: Downtown
```

### Adding a Driver
```
Enter Driver ID: D001
Enter Driver Name: Jane Smith
Enter Driver Location: Uptown
Select Vehicle Type:
1. BIKE
2. AUTO
3. CAR
Enter choice: 3
```

### Requesting a Ride
```
Enter Rider ID: R001
Enter Distance (in km): 10.5
```

### Completing a Ride
```
Enter Ride ID: RIDE001
```

## Fare Calculation

### Default Fare Strategy
- Base Fare: ₹50
- Per KM Rate: ₹10
- Formula: `50 + (distance * 10)`

### Peak Hour Fare Strategy
- Multiplier: 1.5x
- Formula: `(50 + (distance * 10)) * 1.5`

## Ride Status Flow
```
REQUESTED → ASSIGNED → COMPLETED
    ↓
CANCELLED
```

## Error Handling
- Input validation for all user inputs
- Proper exception handling with meaningful error messages
- State validation (e.g., can't complete a ride that's not assigned)
- Null checks and edge case handling

## Extensibility

### Adding a New Ride Matching Strategy
1. Create a new class implementing `RideMatchingStrategy`
2. Implement the `findDriver()` method
3. Add option in Main.java menu to select the new strategy

### Adding a New Fare Strategy
1. Create a new class implementing `FareStrategy`
2. Implement the `calculateFare()` method
3. Add option in Main.java menu to select the new strategy

## Best Practices Followed
- **DRY (Don't Repeat Yourself)**: No duplication in ride allocation logic
- **KISS (Keep It Simple, Stupid)**: Simple entity modeling
- **YAGNI (You Aren't Gonna Need It)**: MVP implementation without feature bloat
- **Law of Demeter**: Services communicate directly with collaborators
- **Separation of Concerns**: Clear layer separation (Model, Service, Strategy)
- **Encapsulation**: Private fields with public getters/setters
- **Immutability**: Enums for fixed value sets

## Testing Scenarios Covered
1. ✅ Register multiple riders
2. ✅ Register multiple drivers with different vehicle types
3. ✅ View available drivers
4. ✅ Request ride with automatic driver assignment
5. ✅ Complete ride with fare calculation
6. ✅ Cancel ride (driver becomes available again)
7. ✅ Switch between ride matching strategies
8. ✅ Switch between fare calculation strategies
9. ✅ Handle edge cases (no available drivers, invalid IDs, etc.)
10. ✅ Track ride status transitions

## Future Enhancements
- Persist data to database or file system
- Add authentication and authorization
- Implement real distance calculation using coordinates
- Add ride history and analytics
- Support for multiple concurrent rides
- Payment gateway integration
- Real-time driver tracking
- Rating and review system

## License
This project is created for educational purposes to demonstrate OOP, SOLID principles, and design patterns.

## Author
Developed as part of AirTribe learning objectives for Low-Level Design (LLD) skills."# RideSystem" 
