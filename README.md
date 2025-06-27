I developed a Java program for an intelligent parcel delivery system that simulates how a logistics company assigns delivery vehicles to customer locations efficiently. The system prioritizes parcels based on urgency, delivery deadlines, and traffic conditions while optimizing routes for timely deliveries.

Key Features of the Program:
Parcel Prioritization

Uses a custom max heap to manage incoming delivery requests, ensuring urgent parcels (e.g., same-day deliveries) and high-priority orders are handled first.

Each parcel has attributes like ID, destination, deadline, and order time.

Vehicle Fleet Management

Tracks available delivery vehicles in real-time, storing details such as vehicle ID, current location, capacity, and status (available/busy).

Uses efficient data structures (arrays or hash tables) for quick vehicle lookups.

Route Optimization

Models the city as a graph where nodes represent delivery zones and edges represent roads with travel times.

Implements Dijkstra’s algorithm to calculate the shortest path for each delivery, ensuring minimal travel time.

Updates vehicle status automatically once dispatched or after completing a delivery.

Implementation Approach
Built custom data structures (graph, heap, and list) from scratch without relying on Java’s built-in libraries.

Designed a modular system to handle parcel assignments, vehicle tracking, and route planning separately for clarity and scalability.

Purpose & Outcome
This program demonstrates how core data structures and algorithms can be applied to solve real-world logistics challenges, optimizing delivery efficiency while meeting deadlines. It serves as a practical simulation for managing fleet operations in dynamic urban environments.
