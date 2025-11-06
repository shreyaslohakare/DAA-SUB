import java.util.*;

class Edge {
    int to;
    double cost;

    Edge(int to, double cost) {
        this.to = to;
        this.cost = cost;
    }
}

public class Assignment5 {

    static final double INF = 1e9;

    public static class Result {
        double minCost;
        List<Integer> path;

        Result(double minCost, List<Integer> path) {
            this.minCost = minCost;
            this.path = path;
        }
    }

    public static Result multistageOptimalPath(List<List<List<Edge>>> stages) {
        int N = stages.size();
        List<List<Double>> dp = new ArrayList<>();
        List<List<Integer>> parent = new ArrayList<>();

       
        int stage0Size = stages.get(0).size();
        dp.add(new ArrayList<>(Collections.nCopies(stage0Size, 0.0)));
        parent.add(new ArrayList<>(Collections.nCopies(stage0Size, -1)));


        for (int i = 1; i < N; i++) {
            int stageSize = stages.get(i).size();
            dp.add(new ArrayList<>(Collections.nCopies(stageSize, INF)));
            parent.add(new ArrayList<>(Collections.nCopies(stageSize, -1)));

            for (int j = 0; j < stageSize; j++) {
                for (int k = 0; k < stages.get(i - 1).size(); k++) {
                    for (Edge e : stages.get(i - 1).get(k)) {
                        if (e.to == j) {
                            double cost = dp.get(i - 1).get(k) + e.cost;
                            if (cost < dp.get(i).get(j)) {
                                dp.get(i).set(j, cost);
                                parent.get(i).set(j, k);
                            }
                        }
                    }
                }
            }
        }

        // Find minimum cost at last stage
        double minCost = INF;
        int lastNode = -1;
        for (int i = 0; i < dp.get(N - 1).size(); i++) {
            if (dp.get(N - 1).get(i) < minCost) {
                minCost = dp.get(N - 1).get(i);
                lastNode = i;
            }
        }

       
        List<Integer> path = new ArrayList<>(Collections.nCopies(N, 0));
        int node = lastNode;
        for (int i = N - 1; i >= 0; i--) {
            path.set(i, node);
            node = parent.get(i).get(node);
        }

        return new Result(minCost, path);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of stages: ");
        int stagesCount = sc.nextInt();

        List<List<List<Edge>>> stages = new ArrayList<>();

        for (int i = 0; i < stagesCount; i++) {
            System.out.print("Enter number of nodes in stage " + (i + 1) + ": ");
            int nodes = sc.nextInt();

            List<List<Edge>> stage = new ArrayList<>();
            for (int j = 0; j < nodes; j++) {
                stage.add(new ArrayList<>());
            }
            stages.add(stage);

            if (i == stagesCount - 1)
                continue; // Last stage has no outgoing edges

            for (int j = 0; j < nodes; j++) {
                System.out.print("Enter number of outgoing edges from node " + j +
                        " in stage " + (i + 1) + ": ");
                int edges = sc.nextInt();

                for (int k = 0; k < edges; k++) {
                    System.out.print("  Edge to node in next stage (0-based index) and cost: ");
                    int to = sc.nextInt();
                    double cost = sc.nextDouble();
                    stages.get(i).get(j).add(new Edge(to, cost));
                }
            }
        }

        Result result = multistageOptimalPath(stages);
        System.out.println("\nOptimal path through stages (0-based node indices): " +
                result.path);
        System.out.println("Minimum cost: " + result.minCost);

        sc.close();
    }
}
/* Sample Output Enter number of stages: 4 
Enter number of nodes in stage 1: 1 
Enter number of outgoing edges from node 0 in stage 1: 3 
Edge to node in next stage (0-based index) and cost: 0 2 
Edge to node in next stage (0-based index) and cost: 1 1 
Edge to node in next stage (0-based index) and cost: 2 3 
Enter number of nodes in stage 2: 3
Enter number of outgoing edges from node 0 in stage 2: 2 
Edge to node in next stage (0-based index) and cost: 0 2 
Edge to node in next stage (0-based index) and cost: 1 3 
Enter number of outgoing edges from node 1 in stage 2: 2 
Edge to node in next stage (0-based index) and cost: 0 6 
Edge to node in next stage (0-based index) and cost: 1 7 
Enter number of outgoing edges from node 2 in stage 2: 2 
Edge to node in next stage (0-based index) and cost: 0 6 
Edge to node in next stage (0-based index) and cost: 1 8 
Enter number of nodes in stage 3: 2
Enter number of outgoing edges from node 0 in stage 3: 2
Edge to node in next stage (0-based index) and cost: 0 6
Edge to node in next stage (0-based index) and cost: 1 8
Enter number of outgoing edges from node 1 in stage 3: 2
Edge to node in next stage (0-based index) and cost: 0 4
Edge to node in next stage (0-based index) and cost: 1 5
Enter number of nodes in stage 4: 2 
Optimal path through stages (0-based node indices): 0 1 0 1 

Minimum cost: 9 */
