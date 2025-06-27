public class DeliverySystem {
    private MaxHeap parcelQueue;
    private VehicleManager vehicleManager;
    private Graph cityMap;
    private Dijkstra dijkstra;
    private int systemTime;
    private int startTime;

    public DeliverySystem(MaxHeap parcelQueue, VehicleManager vehicleManager, Graph cityMap) {
        this.parcelQueue = parcelQueue;
        this.vehicleManager = vehicleManager;
        this.cityMap = cityMap;
        this.dijkstra = new Dijkstra();
        this.startTime = 8 * 60;
    }

    public void processDeliveries() {
        System.out.println("\n=== DELIVERY PROCESS STARTING AT " + formatAbsoluteTime(startTime) + " ===");

        while (parcelQueue.size > 0) {
            ParcelRequest parcel = parcelQueue.extractMax();
            Vehicle vehicle = vehicleManager.findAvailableVehicle(parcel);

            printParcelDetails(parcel);

            if (vehicle != null) {
                systemTime = processDelivery(parcel, vehicle, systemTime);
            } else {
                System.out.println(" No available vehicles for " + parcel.parcelId);
                // Consider putting back in queue or other handling
            }

            vehicleManager.printVehicleStatus();
        }

        printFinalSummary();
    }

    private int processDelivery(ParcelRequest parcel, Vehicle vehicle, int currentTime) {
        // 1. Calculate pickup time (current position to origin)
        int pickupTime = calculateTravelTime(vehicle.currentZone, parcel.originZone);
        int pickupArrivalTime = currentTime + pickupTime;

        // 2. Calculate delivery time (origin to destination)
        int deliveryTime = calculateTravelTime(parcel.originZone, parcel.deliveryZone);
        int deliveryArrivalTime = pickupArrivalTime + deliveryTime;

        // 3. Update vehicle state
        vehicle.currentLoad++;
        vehicle.isAvailable = vehicle.canCarryMore();
        vehicle.currentZone = parcel.deliveryZone;

        // 4. Print delivery timeline
        printDeliveryTimeline(parcel, vehicle, deliveryTime, deliveryArrivalTime);

        // Return updated system time (after delivery completion)
        return deliveryArrivalTime;
    }

    private void printDeliveryTimeline(ParcelRequest parcel, Vehicle vehicle, int deliveryTime,
                                        int deliveryArrival) {
        System.out.println("\n VEHICLE ASSIGNED: " + vehicle.vehicleId);
        System.out.println("   Current Load: " + (vehicle.currentLoad-1) + "→" +
                vehicle.currentLoad + "/" + vehicle.capacity);

        System.out.println("\n PICKUP:");
        System.out.println(" From: " + parcel.originZone);
        System.out.println("\n DELIVERY:");
        System.out.println("   From: " + parcel.originZone +
                " → To: " + parcel.deliveryZone +
                " | Time: " + deliveryTime + " mins");

        System.out.println("\n DELIVERY COMPLETED:");
        System.out.println("   Parcel: " + parcel.parcelId +
                " | Customer: " + parcel.customerName);
        System.out.println("   Deadline: " + parcel.getDeadlineFormatted() +
                " | " + (deliveryArrival <= parcel.deadline ? "On Time" : "Late"));
    }

    private void printParcelDetails(ParcelRequest parcel) {
        System.out.println("\n NEW PARCEL ORDER:");
        System.out.println("   ID: " + parcel.parcelId);
        System.out.println("   Customer: " + parcel.customerName +
                (parcel.isVIP ? " (VIP)" : ""));
        System.out.println("   From: " + parcel.originZone +
                " → To: " + parcel.deliveryZone);
        System.out.println("   Type: " +
                (parcel.isEffectiveSameDay() ? "SAME-DAY" : "STANDARD"));
        System.out.println("   Ordered: " + parcel.getOrderTimeFormatted() +
                " | Deadline: " + parcel.getDeadlineFormatted());
        System.out.println("Order History :" + parcel.orderHistoryCount);
    }

    private void printFinalSummary() {
        System.out.println("\n=== ALL DELIVERIES COMPLETED ");
        vehicleManager.printVehicleStatus();
    }


    public String formatAbsoluteTime(int absoluteTime) {
        // Base date: 01/01/2024
        int baseDay = 1;
        int baseMonth = 1;
        int baseYear = 2024;

        // Calculate total days, hours, and minutes
        int totalDays = absoluteTime / (24 * 60);
        int remainingMinutes = absoluteTime % (24 * 60);
        int hours = remainingMinutes / 60;
        int minutes = remainingMinutes % 60;

        // Calculate final day, month, year (basic calculation assuming 30 days per month)
        int day = baseDay + totalDays;
        int month = baseMonth;
        int year = baseYear;

        while (day > 30) { // Assuming 30 days per month for simplicity
            day -= 30;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }

        // Format hour to 12-hour format with AM/PM
        String ampm = "AM";
        if (hours >= 12) {
            ampm = "PM";
            if (hours > 12) {
                hours -= 12;
            }
        }
        if (hours == 0) {
            hours = 12; // Midnight case
        }
        String formattedMinutes = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);

        // Build the final formatted string
        return String.format("%02d/%02d/%d : %d.%s%s", day, month, year, hours, formattedMinutes, ampm);
    }

    private int calculateTravelTime(String fromZone, String toZone) {
        if (fromZone.equals(toZone)) return 0;

        int[] distances = dijkstra.shortestPaths(cityMap, fromZone);
        int toIndex = cityMap.findZoneIndex(toZone);
        return (toIndex == -1 || distances[toIndex] == Integer.MAX_VALUE) ? -1 : distances[toIndex];
    }
}