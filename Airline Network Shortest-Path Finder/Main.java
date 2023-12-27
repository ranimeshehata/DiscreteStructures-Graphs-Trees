import java.util.*;

class AirportGraph {
    private Map<String, Map<String, Integer>> graph;

    public AirportGraph(List<String> airports, List<String> flights, Map<String, Integer> distances) {
        graph = new HashMap<>();

        // Create nodes (airports)
        for (String airport : airports) {
            graph.put(airport, new HashMap<>());
        }

        // Create edges (flights)
        for (String flight : flights) {
            String[] airportsOnFlight = flight.split("-");
            String source = airportsOnFlight[0];
            String destination = airportsOnFlight[1];
            int distance = distances.get(flight);

            graph.get(source).put(destination, distance);
            graph.get(destination).put(source, distance);
        }
    }

    public List<String> findShortestPath(String source, String destination) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialize distances
        for (String airport : graph.keySet()) {
            distances.put(airport, Integer.MAX_VALUE);
            previous.put(airport, null);
        }
        distances.put(source, 0);
        priorityQueue.add(source);

        // Dijkstra's algorithm
        while (!priorityQueue.isEmpty()) {
            String current = priorityQueue.poll();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            for (Map.Entry<String, Integer> neighbor : graph.get(current).entrySet()) {
                String nextAirport = neighbor.getKey();
                int newDistance = distances.get(current) + neighbor.getValue();
                if (newDistance < distances.get(nextAirport)) {
                    distances.put(nextAirport, newDistance);
                    previous.put(nextAirport, current);
                    priorityQueue.add(nextAirport);
                }
            }
        }

        // Reconstruct the path
        List<String> path = new ArrayList<>();
        String current = destination;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);

        return path;
    }
}

class AirlineRouteFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input airports
        System.out.print("Enter the list of airports: ");
        List<String> airports = Arrays.asList(scanner.nextLine().toUpperCase().split(", "));

        // Input flights
        System.out.print("Enter the flights: ");
        List<String> flights = Arrays.asList(scanner.nextLine().toUpperCase().split(", "));

        // Input distances
        Map<String, Integer> distances = new HashMap<>();
        for (String flight : flights) {
            System.out.print("The distance for " + flight + " (in miles): ");
            int distance = scanner.nextInt();
            // Add input validation for non-negative distances
            if (distance < 0) {
                System.out.println("Distance should be non-negative. Exiting.");
                System.exit(1);
            }
            distances.put(flight, distance);
        }

        // Create the graph
        AirportGraph airportGraph = new AirportGraph(airports, flights, distances);

        // Input source and destination airports
        System.out.print("Enter source airport: ");
        String sourceAirport = scanner.next().toUpperCase();

        // Input validation for source airport
        if (!airports.contains(sourceAirport)) {
            System.out.println("Source airport not found. Exiting.");
            System.exit(1);
        }

        System.out.print("Enter destination airport: ");
        String destinationAirport = scanner.next().toUpperCase();

        // Input validation for destination airport
        if (!airports.contains(destinationAirport)) {
            System.out.println("Destination airport not found. Exiting.");
            System.exit(1);
        }

        // Consume the newline character
        scanner.nextLine();

        List<String> shortestPath = airportGraph.findShortestPath(sourceAirport, destinationAirport);

        // Display the result
        if(shortestPath.size() == 1 && (shortestPath.get(0).equals(sourceAirport) || shortestPath.get(0).equals(destinationAirport))) {
            System.out.println("No flights available from " + sourceAirport + " to " + destinationAirport);
            System.exit(1);
        };
        System.out.print("Shortest path from " + sourceAirport + " to " + destinationAirport + ": ");
        System.out.println(String.join(" - ", shortestPath));
        System.out.println("Total distance: " + calculateTotalDistance(shortestPath, distances) + " miles");

        scanner.close();
    }

    private static int calculateTotalDistance(List<String> path, Map<String, Integer> distances) {
        int totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String flight = path.get(i) + "-" + path.get(i + 1);
            totalDistance += distances.get(flight);
        }
        return totalDistance;
    }
}