import java.util.*;

public class Assignment7 {

    static void greedyColoring(int[][] graph, int V) {
        int[] result = new int[V];
        Arrays.fill(result, -1);

        boolean[] available = new boolean[V];

        // Assign first color to first vertex
        result[0] = 0;

        // Assign colors to remaining vertices
        for (int u = 1; u < V; u++) {
            Arrays.fill(available, false);

            for (int i = 0; i < V; i++) {
                if (graph[u][i] == 1 && result[i] != -1)
                    available[result[i]] = true;
            }

            int cr;
            for (cr = 0; cr < V; cr++) {
                if (!available[cr])
                    break;
            }

            result[u] = cr;
        }

        // Print results
        System.out.println("\nExam Schedule (Course : Time Slot)");
        System.out.println("----------------------------------");
        for (int u = 0; u < V; u++) {
            System.out.println("Course " + (u + 1) + " --> Slot " + (result[u] + 1));
        }

        int totalSlots = Arrays.stream(result).max().getAsInt() + 1;
        System.out.println("\nMinimum number of time slots required: " + totalSlots);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of courses (vertices): ");
        int V = sc.nextInt();

        int[][] graph = new int[V][V];

        System.out.print("Enter number of student conflicts (edges): ");
        int E = sc.nextInt();

        System.out.println("Enter each conflict as: course1 course2");
        System.out.println("(Means these two courses share students and cannot have same slot)");
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph[u - 1][v - 1] = 1;
            graph[v - 1][u - 1] = 1;
        }

        greedyColoring(graph, V);
        sc.close();
    }
}
/*Enter number of courses (vertices): 4
Enter number of student conflicts (edges): 5
Enter each conflict as: course1 course2
(Means these two courses share students and cannot have same slot)
1 2
1 3
2 3
2 4
3 4

Exam Schedule (Course : Time Slot)
----------------------------------
Course 1 --> Slot 1
Course 2 --> Slot 2
Course 3 --> Slot 3
Course 4 --> Slot 1

Minimum number of time slots required: 3*/