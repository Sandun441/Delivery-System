public class VehicleManager {
    Vehicle[] vehicles;
    int vehicleCount;
    private Graph cityMap;
    private Dijkstra dijkstra;

    public VehicleManager(int capacity, Graph cityMap, Dijkstra dijkstra) {
        vehicles = new Vehicle[capacity];
        vehicleCount = 0;
        this.cityMap = cityMap;
        this.dijkstra = dijkstra;
    }


    public void addVehicle(Vehicle v) {
        vehicles[vehicleCount++] = v;
    }
    public Vehicle findAvailableVehicle(ParcelRequest parcel) {
        Vehicle bestVehicle = null;
        int minTravelTime = Integer.MAX_VALUE;
        String preferredZone = parcel.originZone;

        for (int i = 0; i < vehicleCount; i++) {
            Vehicle v = vehicles[i];
            if (v.isAvailable && v.canCarryMore()) {
                // If vehicle is in the same zone as parcel origin, return it immediately
                if (v.currentZone.equals(preferredZone)) {
                    return v;
                }

                // Otherwise calculate travel time
                int travelTime = calculateTravelTime(v.currentZone, preferredZone);
                if (travelTime != -1 && travelTime < minTravelTime) {
                    minTravelTime = travelTime;
                    bestVehicle = v;
                }
            }
        }
        return bestVehicle;
    }
    private int calculateTravelTime(String fromZone, String toZone) {
        if (fromZone.equals(toZone)) {
            return 0;
        }

        int[] distances = dijkstra.shortestPaths(cityMap, fromZone);
        int toIndex = cityMap.findZoneIndex(toZone);
        if (toIndex == -1 || distances[toIndex] == Integer.MAX_VALUE) {
            return -1;
        }
        return distances[toIndex];
    }

    public void printVehicleStatus() {
        System.out.println("\n=== VEHICLE STATUS ===");
        for (int i = 0; i < vehicleCount; i++) {
            Vehicle v = vehicles[i];
            String status = v.isAvailable ?
                    "AVAILABLE (" + v.currentLoad + "/" + v.capacity + ")" :
                    "BUSY (" + v.currentLoad + "/" + v.capacity + ")";
            System.out.println(v.vehicleId + " | Location: " + v.currentZone + " | Status: " + status);
        }
    }
}