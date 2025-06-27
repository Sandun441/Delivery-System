public class Main {
    public static void main(String[] args) {

        Graph cityMap = new Graph(10);
        Dijkstra dijkstra = new Dijkstra();
        MaxHeap parcelQueue = new MaxHeap(10);  // Added startTime parameter
        VehicleManager vehicleManager = new VehicleManager(10, cityMap, dijkstra);

        // Add zones
        cityMap.addZone("New York");
        cityMap.addZone("California");
        cityMap.addZone("Texas");
        cityMap.addEdge("New York", "California", 300);
        cityMap.addEdge("California", "Texas", 300);

        // Add bidirectional routes with travel times in minutes
        cityMap.addEdge("New York", "California", 300);  // 5 hours
        cityMap.addEdge("California", "Texas", 300);

        // Add vehicles with capacities
        vehicleManager.addVehicle(new Vehicle("XE3344", "California", 2));  // Capacity: 2
        vehicleManager.addVehicle(new Vehicle("BEG4356", "New York", 2));  // Capacity: 2


        // Deadline parameter removed since it's calculated automatically
        parcelQueue.insert(new ParcelRequest(800, "P001", "New York", "Texas",
                "Hafsa Sindikar", 500, 25, false, true));  // VIP

        parcelQueue.insert(new ParcelRequest(900, "P002", "California", "Texas",
                "Munchee Musharraf", 500, 25, false, true)); // VIP
        parcelQueue.insert(new ParcelRequest(800, "P003", "New York", "Texas",
                "Sandun Bandara", 500, 25, false, true));  // VIP

        parcelQueue.insert(new ParcelRequest(900, "P004", "California", "Texas",
                "Vinod Warne", 900, 20, true, false)); // VIP
        parcelQueue.insert(new ParcelRequest(800, "P005", "New York", "Texas",
                "Seema Warne", 500, 25, false, false));  // VIP

        parcelQueue.insert(new ParcelRequest(900, "P006", "California", "Texas",
                "Sathya Kaumini", 500, 20, false, false)); // VIP



        // Create delivery system with startTime
        DeliverySystem system = new DeliverySystem(parcelQueue, vehicleManager, cityMap);
        System.out.println("PROCESSING DELIVERIES:");
        system.processDeliveries();

    }
}