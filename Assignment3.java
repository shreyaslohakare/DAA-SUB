import java.util.*;

class Item {
    double utility;
    double weight;
    double ratio; // utility per weight

    Item(double utility, double weight) {
        this.utility = utility;
        this.weight = weight;
        this.ratio = utility / weight;
    }
}

public class Assignment3{

    static double fractionalKnapsack(List<Item> items, double capacity) {
        
        items.sort((a, b) -> Double.compare(b.ratio, a.ratio));

        double totalUtility = 0.0;
        double remaining = capacity;

        for (Item item : items) {
            if (item.weight <= remaining) {
                totalUtility += item.utility;
                remaining -= item.weight;
            } else {
                totalUtility += item.ratio * remaining;
                break;
            }
        }
        return totalUtility;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter capacity of the boat:\n");
        double capacity = sc.nextDouble();

        System.out.print("Enter number of items:\n");
        int n = sc.nextInt();

        List<Item> items = new ArrayList<>();
        System.out.println("Enter utility and weight for each item:");
        for (int i = 0; i < n; i++) {
            double utility = sc.nextDouble();
            double weight = sc.nextDouble();
            items.add(new Item(utility, weight));
        }

        double maxUtility = fractionalKnapsack(items, capacity);
        System.out.println("Maximum utility value = \n" + maxUtility);

        sc.close();
    }
}
/*Enter capacity of the boat:
5
Enter number of items:
4
Enter utility and weight for each item:
70 40
50 20
60 20
30 20
Maximum utility value = 

15.0*/
