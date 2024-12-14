# E-Sharing-System

A Java-based implementation of an electric vehicle sharing system that allows users to rent different types of vehicles from various providers.

## Project Overview

This system simulates an e-sharing platform where users can rent electric vehicles including:
- Electric Cars
- Electric Scooters
- Electric Bikes
- Tiny Scooters

### Key Features

- Multiple vehicle types with different characteristics
- Different provider options (A Bikes, B Mobile, C Share)
- User management with different tariffs and licenses
- Real-time battery monitoring
- Usage-based pricing
- Vehicle state management (lights, locks)

## System Components

### Providers

Three different providers with unique pricing and requirements:
- **A Bikes**
  - Minimum battery requirement: 20%
  - License-free vehicles: 8ct/s
  - Scooters: 11ct/s
  - Cars: 15ct/s

- **B Mobile**
  - Minimum battery requirement: 25%
  - License-free vehicles: 9ct/s
  - Scooters: 11ct/s
  - Cars: 15ct/s

- **C Share**
  - Minimum battery requirement: 30%
  - License-free vehicles: 9ct/s
  - Scooters: 9ct/s
  - Cars: 15ct/s

### Users

Users can have different:
- **Tariffs**
  - Standard (post-paid)
  - Prepaid (requires balance for rental)

- **Licenses**
  - Car only
  - Scooter only
  - Car and Scooter
  - No license (for bikes and tiny scooters)

### Vehicles

#### Electric Cars
- Requires car license
- Side lights for reservation status
- Lockable
- Battery discharge: 6% per second

#### Electric Scooters
- Requires scooter license
- Front, rear, and side lights
- Battery discharge: 4% per second

#### Tiny Scooters
- No license required
- LED status indicator (Red, Green, Yellow, Blue)
- Battery discharge: 2% per second

#### Electric Bikes
- No license required
- Lockable
- Battery discharge: 1% per second

## Vehicle States

### Cars & Scooters
- Side lights indicate reservation status
- Front/rear lights (scooters only) indicate active rental

### Tiny Scooters
- Red: Not available
- Green: Available
- Yellow: Reserved
- Blue: Active rental

### Bikes
- Locked/Unlocked status

## Usage Flow

1. User registers with tariff and license type
2. User checks available vehicles
3. User reserves vehicle (if eligible)
4. Vehicle state changes to reserved
5. User starts rental
6. Vehicle state changes to active
7. User ends rental
8. System calculates charges based on:
   - Duration
   - Provider rates
   - Vehicle type
9. System updates user balance and vehicle status

## Technical Requirements

- Java Development Kit (JDK)
- Prog1Tools library
- IDE (Eclipse/IntelliJ IDEA recommended)

## Project Structure

```
src/
├── main/
│   ├── provider/
│   │   ├── main/
│   │   │   ├── provider/
│   │   │   │   ├── Bikes.java
│   │   │   │   ├── Mobile.java
│   │   │   │   ├── Provider.java
│   │   │   │   └── Share.java
│   │   │   ├── user/
│   │   │   │   └── User.java
│   │   │   ├── vehicles/
│   │   │   │   ├── Bike.java
│   │   │   │   ├── Car.java
│   │   │   │   ├── Scooter.java
│   │   │   │   ├── TinyScooter.java
│   │   │   │   └── Vehicle.java
│   │   │   ├── IProvider.java
│   │   │   └── IShareable.java
│   │   └── main/
│   │       └── manager/
│   │           └── Manager.java
```

## Running the System

1. Clone the repository
2. Open in your preferred IDE
3. Add Prog1Tools to your project libraries
4. Run the Manager class
5. Follow the console prompts to test different scenarios

## Testing Scenarios

The system includes several test scenarios:
1. Standard user renting a scooter
2. Prepaid user rental attempt
3. License restriction testing
4. Car rental process
5. Bike rental process
