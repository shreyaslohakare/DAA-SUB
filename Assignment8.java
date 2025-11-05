import java.util.*;

class Node {
    List<Integer> path;
    int[][] reducedMatrix;
    int cost;
    int vertex;
    int level;

    Node(List<Integer> path, int[][] reducedMatrix, int cost, int vertex, int level) {
        this.path = path;
        this.reducedMatrix = reducedMatrix;
        this.cost = cost;
        this.vertex = vertex;
        this.level = level;
    }
}

public class Assignment8 {
    static final int INF = 999999;

    // Reduce matrix and return reduction cost
    static int reduceMatrix(int[][] mat) {
        int reduction = 0;
        int n = mat.length;

        // Row reduction
        for (int i = 0; i < n; i++) {
            int rowMin = INF;
            for (int j = 0; j < n; j++)
                rowMin = Math.min(rowMin, mat[i][j]);

            if (rowMin != INF && rowMin != 0) {
                reduction += rowMin;
                for (int j = 0; j < n; j++)
                    if (mat[i][j] != INF) mat[i][j] -= rowMin;
            }
        }

        // Column reduction
        for (int j = 0; j < n; j++) {
            int colMin = INF;
            for (int i = 0; i < n; i++)
                colMin = Math.min(colMin, mat[i][j]);

            if (colMin != INF && colMin != 0) {
                reduction += colMin;
                for (int i = 0; i < n; i++)
                    if (mat[i][j] != INF) mat[i][j] -= colMin;
            }
        }

        return reduction;
    }

    // Create new node
    static Node newNode(int[][] parentMatrix, List<Integer> path, int level, int i, int j) {
        int n = parentMatrix.length;
        int[][] childMatrix = new int[n][n];

        for (int x = 0; x < n; x++)
            childMatrix[x] = Arrays.copyOf(parentMatrix[x], n);

        for (int k = 0; k < n; k++) {
            childMatrix[i][k] = INF;
            childMatrix[k][j] = INF;
        }
        childMatrix[j][0] = INF; // prevent returning too early

        return new Node(path, childMatrix, 0, j, level);
    }

    // TSP using Branch and Bound
    static int tspLC(int[][] costMatrix) {
        int n = costMatrix.length;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));

        List<Integer> path = new ArrayList<>();
        path.add(0);

        int[][] rootMatrix = new int[n][n];
        for (int i = 0; i < n; i++)
            rootMatrix[i] = Arrays.copyOf(costMatrix[i], n);

        int rootCost = reduceMatrix(rootMatrix);
        Node root = new Node(path, rootMatrix, rootCost, 0, 0);

        pq.add(root);

        int minCost = INF;
        List<Integer> bestPath = new ArrayList<>();

        while (!pq.isEmpty()) {
            Node minNode = pq.poll();
            int i = minNode.vertex;

            if (minNode.level == n - 1) {
                int lastToFirst = costMatrix[i][0];
                if (lastToFirst != INF && minNode.cost + lastToFirst < minCost) {
                    minCost = minNode.cost + lastToFirst;
                    bestPath = new ArrayList<>(minNode.path);
                    bestPath.add(0);
                }
                continue;
            }

            for (int j = 0; j < n; j++) {
                if (minNode.reducedMatrix[i][j] != INF) {
                    List<Integer> newPath = new ArrayList<>(minNode.path);
                    newPath.add(j);

                    Node child = newNode(minNode.reducedMatrix, newPath, minNode.level + 1, i, j);
                    int cost = minNode.cost + minNode.reducedMatrix[i][j];
                    cost += reduceMatrix(child.reducedMatrix);
                    child.cost = cost;

                    pq.add(child);
                }
            }
        }

        System.out.println("\nOptimal Route: " + bestPath);
        System.out.println("Minimum Cost: " + minCost);
        return minCost;
    }

    public static void main(String[] args) {
        int[][] costMatrix = {
            {INF, 10, 15, 20},
            {10, INF, 35, 25},
            {15, 35, INF, 30},
            {20, 25, 30, INF}
        };

        tspLC(costMatrix);
    }
}
/*int[][] costMatrix = {
    {INF, 10, 15, 20},
    {10, INF, 35, 25},
    {15, 35, INF, 30},
    {20, 25, 30, INF}
};
Optimal Route: [0, 1, 3, 2, 0]
Minimum Cost: 80
    */

