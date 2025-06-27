public class Dijkstra {
    public int[] shortestPaths(Graph graph, String startZone) {
        int[] distances = new int[graph.zoneCount];
        boolean[] visited = new boolean[graph.zoneCount];
        int startIndex = graph.findZoneIndex(startZone);

        // Initialize distances
        for (int i = 0; i < graph.zoneCount; i++) {
            distances[i] = Integer.MAX_VALUE;
        }

        if (startIndex == -1) return distances; // Invalid start zone

        distances[startIndex] = 0;

        for (int i = 0; i < graph.zoneCount; i++) {
            int minIndex = findMinDistance(distances, visited);
            if (minIndex == -1) break; // All remaining nodes are inaccessible

            visited[minIndex] = true;
            updateNeighbors(graph, minIndex, distances, visited);
        }

        return distances;
    }

    private int findMinDistance(int[] distances, boolean[] visited) {
        int minIndex = -1;
        int minDistance = Integer.MAX_VALUE;

        for (int j = 0; j < distances.length; j++) {
            if (!visited[j] && distances[j] < minDistance) {
                minDistance = distances[j];
                minIndex = j;
            }
        }
        return minIndex;
    }

    private void updateNeighbors(Graph graph, int minIndex, int[] distances, boolean[] visited) {
        Graph.Edge edge = graph.adjacencyList[minIndex];
        while (edge != null) {
            int neighborIndex = graph.findZoneIndex(edge.destination);
            if (neighborIndex != -1 && !visited[neighborIndex]) {
                int newDist = distances[minIndex] + edge.travelTime;
                if (newDist < distances[neighborIndex]) {
                    distances[neighborIndex] = newDist;
                }
            }
            edge = edge.next;
        }
    }
}