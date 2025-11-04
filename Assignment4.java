import java.util.*;

class Edge {
    int to;
    double weight;

    Edge(int to, double weight) {
        this.to = to;
        this.weight = weight;
    }
}

class Node implements Comparable<Node> {
    int id;
    double dist;

    Node(int id, double dist) {
        this.id = id;
        this.dist = dist;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.dist, other.dist);
    }
}

public class Assignment4 {

    static double[] dijkstra(int N, int source, List<List<Edge>> adj, int[] parent) {
        double[] dist = new double[N];
        Arrays.fill(dist, 1e9);
        dist[source] = 0.0;
        Arrays.fill(parent, -1);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.id;
            double d = current.dist;

            if (d > dist[u]) continue;

            for (Edge edge : adj.get(u)) {
                int v = edge.to;
                double w = edge.weight;
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        return dist;
    }

    static List<Integer> reconstructPath(int dest, int[] parent) {
        List<Integer> path = new ArrayList<>();
        for (int v = dest; v != -1; v = parent[v]) {
            path.add(v);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of intersections (nodes) and roads (edges): ");
        int N = sc.nextInt();
        int M = sc.nextInt();

        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) adj.add(new ArrayList<>());

        System.out.println("Enter edges (u v weight):");
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            double w = sc.nextDouble();
            adj.get(u).add(new Edge(v, w));
            adj.get(v).add(new Edge(u, w));
        }

        System.out.print("Enter ambulance starting location (source node): ");
        int source = sc.nextInt();

        System.out.print("Enter number of hospitals: ");
        int H = sc.nextInt();
        int[] hospitals = new int[H];
        System.out.println("Enter hospital node indices:");
        for (int i = 0; i < H; i++) hospitals[i] = sc.nextInt();

        int[] parent = new int[N];
        double[] dist = dijkstra(N, source, adj, parent);

        double minDist = 1e9;
        int nearestHospital = -1;
        for (int h : hospitals) {
            if (dist[h] < minDist) {
                minDist = dist[h];
                nearestHospital = h;
            }
        }

        List<Integer> path = reconstructPath(nearestHospital, parent);

        System.out.println("\nNearest hospital: Node " + nearestHospital);
        System.out.println("Shortest travel time: " + minDist + " minutes");
        System.out.print("Path: ");
        for (int v : path) System.out.print(v + " ");
        System.out.println();

        sc.close();
    }
}
/*Enter number of intersections (nodes) and roads (edges): 6 8
Enter edges (u v weight):
0 1 4
0 2 2
1 2 1
1 3 5
2 3 8
2 4 10
3 4 2
3 5 6
Enter ambulance starting location (source node): 0
Enter number of hospitals: 2
Enter hospital node indices:
4 5 

Nearest hospital: Node 4
Shortest travel time: 10.0 minutes
Path: 0 2 1 3 4*/