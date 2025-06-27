public class Graph {
    String[] zones;
    Edge[] adjacencyList;
    int zoneCount;

    public Graph(int maxZones) {
        zones = new String[maxZones];
        adjacencyList = new Edge[maxZones];
        zoneCount = 0;
    }

    public void addZone(String zone) {
        if (findZoneIndex(zone) != -1) return; // Avoid duplicates
        zones[zoneCount++] = zone;
    }

    // Add bidirectional edge
    public void addEdge(String from, String to, int travelTime) {
        addDirectedEdge(from, to, travelTime);
        addDirectedEdge(to, from, travelTime); // Add reverse direction
    }

    private void addDirectedEdge(String from, String to, int travelTime) {
        int fromIndex = findZoneIndex(from);
        if (fromIndex == -1) return;

        Edge edge = new Edge(to, travelTime);
        edge.next = adjacencyList[fromIndex];
        adjacencyList[fromIndex] = edge;
    }

    public int findZoneIndex(String zone) {
        for (int i = 0; i < zoneCount; i++) {
            if (zones[i].equals(zone)) return i;
        }
        return -1;
    }

    public Edge getEdges(String zone) {
        int index = findZoneIndex(zone);
        return adjacencyList[index];
    }

    // Edge class should be defined here
    public static class Edge {
        String destination;
        int travelTime;
        Edge next;

        public Edge(String destination, int travelTime) {
            this.destination = destination;
            this.travelTime = travelTime;
        }
    }
}