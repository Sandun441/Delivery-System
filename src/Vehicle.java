public class Vehicle {
    public String vehicleId;
    public String currentZone;
    public int capacity;
    public int currentLoad;
    public boolean isAvailable;

    public Vehicle(String vehicleId, String currentZone, int capacity) {
        this.vehicleId = vehicleId;
        this.currentZone = currentZone;
        this.capacity = capacity;
        this.currentLoad = 0;
        this.isAvailable = true;
    }

    public boolean canCarryMore() {
        return currentLoad < capacity;
    }
}